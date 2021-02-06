package com.zzx.p2p.base.domain;

import com.zzx.p2p.base.util.BidConst;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 用户对应的账户对象
 *
 * @author zzx
 * @date 2021-02-05 10:10:37
 */
@Getter
@Setter
public class Account extends BaseDomain {
    /** 版本号 */
    private int version;
    /** 交易密码 */
    private String tradePassword;
    /** 账户可用余额 */
    private BigDecimal usableAmount = BidConst.ZERO;
    /** 账户冻结金额 */
    private BigDecimal frozenAmount = BidConst.ZERO;
    /** 账户待收利息 */
    private BigDecimal unReceiveInterest = BidConst.ZERO;
    /** 账户待收本金 */
    private BigDecimal unReceivePrincipal = BidConst.ZERO;
    /** 账户待还金额 */
    private BigDecimal unReturnAmount = BidConst.ZERO;
    /** 账户授信额度 */
    private BigDecimal borrowLimit = BidConst.INIT_BORROW_LIMIT;
    /** 账户剩余授信金额 */
    private BigDecimal remainBorrowLimit = BidConst.INIT_BORROW_LIMIT;

    /**
     *  账户的总金额
     * @return
     */
    public BigDecimal getTotalAmount(){
        return usableAmount.add(frozenAmount).add(unReceivePrincipal);
    }
}
