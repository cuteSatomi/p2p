package com.zzx.p2p.business.service.impl;

import com.zzx.p2p.base.domain.Account;
import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.base.service.AccountService;
import com.zzx.p2p.base.util.UserContext;
import com.zzx.p2p.business.domain.RechargeOffline;
import com.zzx.p2p.business.mapper.RechargeOfflineMapper;
import com.zzx.p2p.business.query.RechargeOfflineQueryObject;
import com.zzx.p2p.business.service.AccountFlowService;
import com.zzx.p2p.business.service.RechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zzx
 * @date 2021-02-23 16:00:28
 */
@Service
public class RechargeOfflineServiceImpl implements RechargeOfflineService {
    @Autowired
    private RechargeOfflineMapper rechargeOfflineMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountFlowService accountFlowService;

    @Override
    public void apply(RechargeOffline recharge) {
        recharge.setApplier(UserContext.getCurrent());
        recharge.setApplyTime(new Date());
        recharge.setState(RechargeOffline.STATE_NORMAL);
        rechargeOfflineMapper.insert(recharge);
    }

    @Override
    public PageResult query(RechargeOfflineQueryObject qo) {
        int count = rechargeOfflineMapper.queryForCount(qo);
        if (count > 0) {
            List<RechargeOffline> list = rechargeOfflineMapper.query(qo);
            return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void audit(Long id, String remark, int state) {
        // 得到线下充值对象
        RechargeOffline r = rechargeOfflineMapper.selectByPrimaryKey(id);
        if (r != null && r.getState() == RechargeOffline.STATE_NORMAL) {
            r.setAuditor(UserContext.getCurrent());
            r.setAuditTime(new Date());
            r.setRemark(remark);
            r.setState(state);
            if (state == RechargeOffline.STATE_AUDIT) {
                // 得到申请人的account对象
                Account applierAccount = accountService.get(r.getApplier().getId());
                // 增加账户可用金额
                applierAccount.setUsableAmount(applierAccount.getUsableAmount().add(r.getAmount()));
                // 生成一条流水
                accountFlowService.rechargeFlow(r, applierAccount);
                accountService.update(applierAccount);
            }
            rechargeOfflineMapper.updateByPrimaryKey(r);
        }
    }
}
