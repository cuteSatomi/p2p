package com.zzx.p2p.business.service.impl;

import com.zzx.p2p.base.domain.Account;
import com.zzx.p2p.base.domain.UserInfo;
import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.base.service.AccountService;
import com.zzx.p2p.base.service.UserInfoService;
import com.zzx.p2p.base.util.BidConst;
import com.zzx.p2p.base.util.BitStatesUtils;
import com.zzx.p2p.base.util.UserContext;
import com.zzx.p2p.business.domain.Bid;
import com.zzx.p2p.business.domain.BidRequest;
import com.zzx.p2p.business.domain.BidRequestAuditHistory;
import com.zzx.p2p.business.mapper.BidMapper;
import com.zzx.p2p.business.mapper.BidRequestAuditHistoryMapper;
import com.zzx.p2p.business.mapper.BidRequestMapper;
import com.zzx.p2p.business.query.BidRequestQueryObject;
import com.zzx.p2p.business.service.AccountFlowService;
import com.zzx.p2p.business.service.BidRequestService;
import com.zzx.p2p.business.service.SystemAccountService;
import com.zzx.p2p.business.util.CalculatetUtil;
import com.zzx.p2p.business.util.DecimalFormatUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zzx
 * @date 2021-02-20 17:34:02
 */
@Service
public class BidRequestServiceImpl implements BidRequestService {
    @Autowired
    private BidRequestMapper bidRequestMapper;

    @Autowired
    private BidMapper bidMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountFlowService accountFlowService;

    @Autowired
    private BidRequestAuditHistoryMapper bidRequestAuditHistoryMapper;

    @Autowired
    private SystemAccountService systemAccountService;

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

    @Override
    public List<BidRequest> listIndex(int size) {
        BidRequestQueryObject qo = new BidRequestQueryObject();
        qo.setBidRequestStates(new int[]{BidConst.BID_REQUEST_STATE_BIDDING,
                BidConst.BID_REQUEST_STATE_PAYING_BACK,
                BidConst.BID_REQUEST_STATE_COMPLETE_PAY_BACK});
        qo.setPageSize(size);
        qo.setCurrentPage(1);
        qo.setOrderBy("bid_request_state");
        qo.setOrderType("asc");
        return bidRequestMapper.query(qo);
    }

    @Override
    public void bid(Long bidRequestId, BigDecimal amount) {
        // 一些参数检查
        BidRequest br = get(bidRequestId);
        Account currentAccount = accountService.get(UserContext.getCurrent().getId());
        if (    // 借款存在
                br != null
                        // 借款状态为招标中
                        && br.getBidRequestState() == BidConst.BID_REQUEST_STATE_BIDDING
                        // 当前用户不是借款人
                        && !br.getCreateUser().getId().equals(UserContext.getCurrent().getId())
                        // 当前用户账户余额 > 投标金额
                        && currentAccount.getUsableAmount().compareTo(amount) >= 0
                        // 投标金额 >= 最小投标金额
                        && amount.compareTo(br.getMinBidAmount()) >= 0
                        // 投标金额 <= 借款剩余投标金额
                        && amount.compareTo(br.getRemainAmount()) <= 0) {

            // 执行投标
            // 创建一个投标对象，设置相关属性
            Bid bid = new Bid();
            bid.setActualRate(br.getCurrentRate());
            bid.setAvailableAmount(amount);
            bid.setBidRequestId(br.getId());
            bid.setBidRequestTitle(br.getTitle());
            bid.setBidTime(new Date());
            bid.setBidUser(UserContext.getCurrent());
            bidMapper.insert(bid);

            // 得到投标人账户，修改账户信息
            currentAccount.setUsableAmount(currentAccount.getUsableAmount().subtract(amount));
            currentAccount.setFrozenAmount(currentAccount.getFrozenAmount().add(amount));
            // 生成一条投标流水
            accountFlowService.bid(bid, currentAccount);
            // 修改借款相关信息
            br.setBidCount(br.getBidCount() + 1);
            br.setCurrentSum(br.getCurrentSum().add(amount));
            // 判断当前标是否投满，
            if (br.getBidRequestAmount().equals(br.getCurrentSum())) {
                // 修改标的状态
                br.setBidRequestState(BidConst.BID_REQUEST_STATE_APPROVE_PENDING_1);
            }

            accountService.update(currentAccount);
            update(br);
        }
    }

    @Override
    public void fullAudit1(Long id, int state, String remark) {
        // 得到借款对象
        BidRequest br = get(id);
        if (br != null && br.getBidRequestState() == BidConst.BID_REQUEST_STATE_APPROVE_PENDING_1) {
            // 创建一个借款审核流程对象
            BidRequestAuditHistory history = new BidRequestAuditHistory();
            history.setApplier(br.getCreateUser());
            history.setApplyTime(new Date());
            history.setAuditor(UserContext.getCurrent());
            history.setAuditTime(new Date());
            history.setBidRequestId(br.getId());
            history.setRemark(remark);
            history.setState(state);
            history.setAuditType(BidRequestAuditHistory.FULL_AUDIT_1);
            bidRequestAuditHistoryMapper.insert(history);

            // 判断审核状态
            if (state == BidRequestAuditHistory.STATE_AUDIT) {
                // 审核通过则将状态修改为满标二审
                br.setBidRequestState(BidConst.BID_REQUEST_STATE_APPROVE_PENDING_2);
            } else {
                // 审核不通过
                // 修改借款状态
                br.setBidRequestState(BidConst.BID_REQUEST_STATE_REJECTED);
                // 退钱
                returnMoney(br);
                // 移除借款人借款的状态码
                UserInfo borrowUser = userInfoService.get(br.getCreateUser().getId());
                borrowUser.removeState(BitStatesUtils.OP_HAS_BID_REQUEST_PROCESS);
                userInfoService.update(borrowUser);
            }
            update(br);
        }
    }

    @Override
    public void fullAudit2(Long id, int state, String remark) {
        // 得到借款对象，判断状态
        BidRequest br = get(id);
        if (br != null && br.getBidRequestState() == BidConst.BID_REQUEST_STATE_APPROVE_PENDING_2) {
            // 创建一个借款的审核流程对象，并设置相关参数
            BidRequestAuditHistory history = new BidRequestAuditHistory();
            history.setApplier(br.getCreateUser());
            history.setApplyTime(new Date());
            history.setAuditor(UserContext.getCurrent());
            history.setAuditTime(new Date());
            history.setBidRequestId(br.getId());
            history.setRemark(remark);
            history.setState(state);
            history.setAuditType(BidRequestAuditHistory.FULL_AUDIT_2);
            bidRequestAuditHistoryMapper.insert(history);

            if (state == BidRequestAuditHistory.STATE_AUDIT) {
                // 审核通过
                // 修改借款状态为还款中
                br.setBidRequestState(BidConst.BID_REQUEST_STATE_PAYING_BACK);

                // 借款人
                // 查出借款人账户
                Account borrowAccount = accountService.get(br.getCreateUser().getId());
                // 借款人账户余额增加
                borrowAccount.setUsableAmount(borrowAccount.getUsableAmount().add(br.getBidRequestAmount()));
                // 生成收款流水
                accountFlowService.borrowSuccess(br, borrowAccount);
                // 修改待还本息
                borrowAccount.setUnReturnAmount(borrowAccount.getUnReturnAmount()
                        .add(br.getTotalRewardAmount())
                        .add(br.getTotalRewardAmount()));
                // 修改可用信用额度
                borrowAccount.setRemainBorrowLimit(borrowAccount.getRemainBorrowLimit().subtract(br.getBidRequestAmount()));
                // 移除借款人借款进行中的状态码
                UserInfo borrowUser = userInfoService.get(br.getCreateUser().getId());
                borrowUser.removeState(BitStatesUtils.OP_HAS_BID_REQUEST_PROCESS);
                userInfoService.update(borrowUser);
                // 支付借款手续费
                BigDecimal manageChargeFee = CalculatetUtil.calAccountManagementCharge(br.getBidRequestAmount());
                // 可用余额减少
                borrowAccount.setUsableAmount(borrowAccount.getUsableAmount().subtract(manageChargeFee));
                // 生成支付借款手续费流水
                accountFlowService.borrowChargeFee(manageChargeFee, br, borrowAccount);
                // 平台收取借款手续费
                systemAccountService.chargeBorrowFee(br, manageChargeFee);

                // 投资人
                Map<Long, Account> updates = new HashMap<Long, Account>();
                BigDecimal totalBidInterest = BidConst.ZERO;
                // 遍历投标
                for (int i = 1; i <= br.getBids().size(); i++) {
                    Bid bid = br.getBids().get(i - 1);
                    // 根据投标减少投资人的冻结金额
                    Long bidUserId = bid.getBidUser().getId();
                    Account bidAccount = updates.get(bidUserId);
                    if (bidAccount == null) {
                        bidAccount = accountService.get(bidUserId);
                        updates.put(bidUserId, bidAccount);
                    }
                    bidAccount.setFrozenAmount(bidAccount.getFrozenAmount().subtract(bid.getAvailableAmount()));
                    // 生成成功投标流水
                    accountFlowService.bidSuccess(bid, bidAccount);
                    // 计算代收利息和代收本金，待收本金=这次的投标金额
                    bidAccount.setUnReceivePrincipal(bidAccount.getUnReceivePrincipal().add(bid.getAvailableAmount()));
                    // 如果当前投标是整个投标列表中的最后一个投标;这个投标的利息=借款总回报利息-累加的投标利息|
                    BigDecimal bidInterest = BidConst.ZERO;
                    if (i < br.getBids().size()) {
                        // 待收利息=投标金额/借款总金额*借款总回报利息
                        bidInterest = bid.getAvailableAmount().divide(br.getBidRequestAmount(),
                                BidConst.CAL_SCALE, RoundingMode.HALF_UP).multiply(br.getTotalRewardAmount());

                        bidInterest = DecimalFormatUtil.formatBigDecimal(bidInterest, BidConst.STORE_SCALE);
                        // 累加
                        totalBidInterest = totalBidInterest.add(bidInterest);
                    } else {
                        bidInterest = br.getTotalRewardAmount().subtract(totalBidInterest);
                    }
                    bidAccount.setUnReceiveInterest(bidAccount.getUnReceiveInterest().add(bidInterest));
                }

                // 生成还款对象和回款对象

            } else {
                // 审核拒绝
                // 1.修改借款状态
                br.setBidRequestState(BidConst.BID_REQUEST_STATE_REJECTED);
                // 2.退钱
                returnMoney(br);
                // 3.移除借款人借款的状态码
                UserInfo borrowUser = userInfoService.get(br.getCreateUser().getId());
                borrowUser.removeState(BitStatesUtils.OP_HAS_BID_REQUEST_PROCESS);
                userInfoService.update(borrowUser);
            }
            update(br);
        }
    }

    /**
     * 满标审核被拒绝，将投标人的钱退回
     *
     * @param br
     */
    private void returnMoney(BidRequest br) {
        Map<Long, Account> updates = new HashMap<Long, Account>();
        // 遍历投标列表
        for (Bid bid : br.getBids()) {
            // 针对每一个bid进行退款
            // 找到投标人对应的账户
            Long accountId = bid.getBidUser().getId();
            Account bidAccount = updates.get(accountId);
            if (bidAccount == null) {
                bidAccount = accountService.get(bid.getBidUser().getId());
                updates.put(accountId, bidAccount);
            }
            // 账户可用余额增加，冻结金额减少
            bidAccount.setUsableAmount(bidAccount.getUsableAmount().add(bid.getAvailableAmount()));
            bidAccount.setFrozenAmount(bidAccount.getFrozenAmount().subtract(bid.getAvailableAmount()));
            // 生成退款流水
            accountFlowService.returnMoney(bid, bidAccount);
        }
        for (Account account : updates.values()) {
            accountService.update(account);
        }
    }
}
