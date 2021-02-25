package com.zzx.p2p.business.service;

import com.zzx.p2p.base.domain.Account;
import com.zzx.p2p.business.domain.Bid;
import com.zzx.p2p.business.domain.BidRequest;
import com.zzx.p2p.business.domain.RechargeOffline;

import java.math.BigDecimal;

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

    /**
     * 生成收款流水
     *
     * @param br
     * @param borrowAccount
     */
    void borrowSuccess(BidRequest br, Account borrowAccount);

    /**
     * 生成支付借款成功手续费流水
     *
     * @param manageChargeFee
     * @param br
     * @param borrowAccount
     */
    void borrowChargeFee(BigDecimal manageChargeFee, BidRequest br, Account borrowAccount);

    /**
     * 生成投资人成功投标流水
     *
     * @param bid
     * @param bidAccount
     */
    void bidSuccess(Bid bid, Account bidAccount);
}
