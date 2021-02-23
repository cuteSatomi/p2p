package com.zzx.p2p.business.service.impl;

import com.zzx.p2p.base.domain.Account;
import com.zzx.p2p.base.util.BidConst;
import com.zzx.p2p.business.domain.AccountFlow;
import com.zzx.p2p.business.domain.RechargeOffline;
import com.zzx.p2p.business.mapper.AccountFlowMapper;
import com.zzx.p2p.business.service.AccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zzx
 * @date 2021-02-23 21:35
 */
@Service
public class AccountFlowServiceImpl implements AccountFlowService {
    @Autowired
    private AccountFlowMapper accountFlowMapper;

    @Override
    public void rechargeFlow(RechargeOffline r, Account account) {
        AccountFlow flow = new AccountFlow();
        flow.setAccountId(account.getId());
        flow.setAccountType(BidConst.ACCOUNT_ACTION_TYPE_RECHARGE_OFFLINE);
        flow.setAmount(r.getAmount());
        flow.setFrozenAmount(account.getFrozenAmount());
        flow.setNote("线下充值成功，充值金额为：" + r.getAmount());
        flow.setTradeTime(new Date());
        flow.setUsableAmount(account.getUsableAmount());

        accountFlowMapper.insert(flow);
    }
}
