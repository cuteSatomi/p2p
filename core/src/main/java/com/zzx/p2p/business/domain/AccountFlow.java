package com.zzx.p2p.business.domain;

import com.zzx.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户流水对象
 *
 * @author zzx
 * @date 2021-02-23 20:53
 */
@Getter
@Setter
public class AccountFlow extends BaseDomain {
    /** 流水是关于哪个账户的 */
    private Long accountId;
    /** 账户发生变化的时间 */
    private Date tradeTime;
    /** 这次账户发生变化的金额 */
    private BigDecimal amount;
    /** 发生变化后的可用余额 */
    private BigDecimal usableAmount;
    /** 发生变化后的冻结金额 */
    private BigDecimal frozenAmount;
    /** 这次交易的类型 */
    private int accountType;
    /** 说明 */
    private String note;
}
