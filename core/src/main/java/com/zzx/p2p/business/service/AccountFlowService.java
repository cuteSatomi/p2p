package com.zzx.p2p.business.service;

import com.zzx.p2p.base.domain.Account;
import com.zzx.p2p.business.domain.RechargeOffline;

/**
 * 账户流水服务
 *
 * @author zzx
 * @date 2021-02-23 21:35
 */
public interface AccountFlowService {
    /**
     * 账户充值流水
     * @param r
     * @param applierAccount
     */
    void rechargeFlow(RechargeOffline r, Account applierAccount);
}
