package com.zzx.p2p.business.service;

import com.zzx.p2p.business.domain.BidRequest;
import com.zzx.p2p.business.domain.SystemAccount;

import java.math.BigDecimal;

/**
 * 平台账户对象
 *
 * @author zzx
 * @date 2021-02-25 14:14:32
 */
public interface SystemAccountService {
    void update(SystemAccount systemAccount);

    /**
     * 检查并初始化系统账户
     */
    void initAccount();

    /**
     * 系统账户收到借款管理费
     *
     * @param br
     * @param manageChargeFee
     */
    void chargeBorrowFee(BidRequest br, BigDecimal manageChargeFee);
}
