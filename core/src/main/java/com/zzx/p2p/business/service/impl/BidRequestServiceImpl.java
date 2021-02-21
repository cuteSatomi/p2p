package com.zzx.p2p.business.service.impl;

import com.zzx.p2p.base.domain.Account;
import com.zzx.p2p.base.domain.UserInfo;
import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.base.service.AccountService;
import com.zzx.p2p.base.service.UserInfoService;
import com.zzx.p2p.base.util.BidConst;
import com.zzx.p2p.base.util.BitStatesUtils;
import com.zzx.p2p.base.util.UserContext;
import com.zzx.p2p.business.domain.BidRequest;
import com.zzx.p2p.business.domain.BidRequestAuditHistory;
import com.zzx.p2p.business.mapper.BidRequestAuditHistoryMapper;
import com.zzx.p2p.business.mapper.BidRequestMapper;
import com.zzx.p2p.business.query.BidRequestQueryObject;
import com.zzx.p2p.business.service.BidRequestService;
import com.zzx.p2p.business.util.CalculatetUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zzx
 * @date 2021-02-20 17:34:02
 */
@Service
public class BidRequestServiceImpl implements BidRequestService {
    @Autowired
    private BidRequestMapper bidRequestMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BidRequestAuditHistoryMapper bidRequestAuditHistoryMapper;

    @Override
    public void update(BidRequest bidRequest) {
        int ret = bidRequestMapper.updateByPrimaryKey(bidRequest);
        if (ret == 0) {
            throw new RuntimeException("乐观锁失败  bidRequest:" + bidRequest.getId());
        }
    }

    @Override
    public boolean canApplyBidRequest(Long loginInfoId) {
        // 得到用户
        UserInfo userInfo = userInfoService.get(loginInfoId);
        // 判断基本资料、视频认证、实名认证、风控资料得分大于30分、没有借款在流程中
        return userInfo != null
                && userInfo.getIsBasicInfo()
                && userInfo.getIsRealAuth()
                && userInfo.getIsVideoAuth()
                && userInfo.getScore() >= BidConst.BASE_BORROW_SCORE
                && !userInfo.getIsBidRequestProcess();
    }

    @Override
    public void apply(BidRequest bidRequestForm) {
        Long id = UserContext.getCurrent().getId();
        Account account = accountService.get(id);
        if (
            // 首先判断是否有借款申请条件
                canApplyBidRequest(id)
                        // 系统最小借款金额 <= 借款金额
                        && bidRequestForm.getBidRequestAmount().compareTo(BidConst.SMALLEST_BID_REQUEST_AMOUNT) >= 0
                        // 借款金额 <= 剩余信用额度
                        && bidRequestForm.getBidRequestAmount().compareTo(account.getRemainBorrowLimit()) <= 0
                        // 5 <= 利息
                        && bidRequestForm.getCurrentRate().compareTo(BidConst.SMALLEST_CURRENT_RATE) >= 0
                        // 利息 <= 20
                        && bidRequestForm.getCurrentRate().compareTo(BidConst.MAX_CURRENT_RATE) <= 0
                        // 最小投标金额 >= 系统最小投标金额
                        && bidRequestForm.getMinBidAmount().compareTo(BidConst.SMALLEST_BID_AMOUNT) >= 0) {

            // 满足上述条件之后创建一个新的bidRequest对象设置相关参数
            BidRequest bidRequest = new BidRequest();
            // 设置表单中的值
            bidRequest.setBidRequestAmount(bidRequestForm.getBidRequestAmount());
            bidRequest.setCurrentRate(bidRequestForm.getCurrentRate());
            bidRequest.setMonths2Return(bidRequestForm.getMonths2Return());
            bidRequest.setReturnType(bidRequestForm.getReturnType());
            bidRequest.setMinBidAmount(bidRequestForm.getMinBidAmount());
            bidRequest.setDisableDays(bidRequestForm.getDisableDays());
            bidRequest.setTitle(bidRequestForm.getTitle());
            bidRequest.setDescription(bidRequestForm.getDescription());
            // 设置一些其他的必要值
            bidRequest.setApplyTime(new Date());
            // 将状态设置为待发布
            bidRequest.setBidRequestState(BidConst.BID_REQUEST_STATE_PUBLISH_PENDING);
            bidRequest.setCreateUser(UserContext.getCurrent());
            bidRequest.setTotalRewardAmount(CalculatetUtil.calTotalInterest(bidRequest.getReturnType(),
                    bidRequest.getBidRequestAmount(), bidRequest.getCurrentRate(), bidRequest.getMonths2Return()));
            // 保存
            bidRequestMapper.insert(bidRequest);
            // 给借款人添加一个状态码
            UserInfo userInfo = userInfoService.get(id);
            userInfo.addState(BitStatesUtils.OP_HAS_BID_REQUEST_PROCESS);
            userInfoService.update(userInfo);
        }
    }

    @Override
    public PageResult query(BidRequestQueryObject qo) {
        int count = bidRequestMapper.queryForCount(qo);
        if (count > 0) {
            List<BidRequest> list = bidRequestMapper.query(qo);
            return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void publishAudit(Long id, int state, String remark) {
        // 查出bidRequest
        BidRequest bidRequest = bidRequestMapper.selectByPrimaryKey(id);
        if (bidRequest != null && bidRequest.getBidRequestState() == BidConst.BID_REQUEST_STATE_PUBLISH_PENDING) {
            // 当前是待发布状态,创建审核历史对象
            BidRequestAuditHistory history = new BidRequestAuditHistory();
            // 设置相关属性
            history.setApplier(bidRequest.getCreateUser());
            history.setApplyTime(bidRequest.getApplyTime());
            history.setAuditType(BidRequestAuditHistory.PUBLISH_AUDIT);
            history.setAuditor(UserContext.getCurrent());
            history.setAuditTime(new Date());
            history.setRemark(remark);
            history.setState(state);
            history.setBidRequestId(bidRequest.getId());
            bidRequestAuditHistoryMapper.insert(history);

            if (state == BidRequestAuditHistory.STATE_AUDIT) {
                // 审核通过
                bidRequest.setBidRequestState(BidConst.BID_REQUEST_STATE_BIDDING);
                bidRequest.setDisableDate(DateUtils.addDays(new Date(), bidRequest.getDisableDays()));
                bidRequest.setPublishTime(new Date());
                bidRequest.setNote(remark);
            } else {
                // 审核拒绝
                bidRequest.setBidRequestState(BidConst.BID_REQUEST_STATE_PUBLISH_REFUSE);
                UserInfo applier = userInfoService.get(bidRequest.getCreateUser().getId());
                applier.removeState(BitStatesUtils.OP_HAS_BID_REQUEST_PROCESS);
                userInfoService.update(applier);
            }
            update(bidRequest);
        }
    }

    @Override
    public BidRequest get(Long id) {
        return bidRequestMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BidRequestAuditHistory> listAuditHistoryByBidRequest(Long id) {
        return bidRequestAuditHistoryMapper.listByBidRequest(id);
    }
}
