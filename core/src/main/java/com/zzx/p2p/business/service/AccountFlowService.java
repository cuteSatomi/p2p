package com.zzx.p2p.business.service;

import com.zzx.p2p.base.domain.Account;
import com.zzx.p2p.business.domain.Bid;
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
     *
     * @param r
     * @param applierAccount
     */
    void rechargeFlow(RechargeOffline r, Account applierAccount);

    /**
     * 投标流水
     *
     * @param bid
     * @param currentAccount
     */
    void bid(Bid bid, Account currentAccount);

    /**
     * 满标审核拒绝退款流水
     *
     * @param bid
     * @param bidAccount
     */
    void returnMoney(Bid bid, Account bidAccount);
}
