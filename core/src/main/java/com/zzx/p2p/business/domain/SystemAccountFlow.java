package com.zzx.p2p.business.domain;

import com.zzx.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 系统账户流水，专门用于记录系统账户的流水变化
 *
 * @author Stef
 */
@Getter
@Setter
public class SystemAccountFlow extends BaseDomain {

    private static final long serialVersionUID = 6745295563470056376L;

    /** 流水创建时间 */
    private Date createdDate;
    /** 系统账户流水类型 */
    private int accountActionType;
    /** 流水相关金额 */
    private BigDecimal amount;
    /** 流水账备注 */
    private String note;
    /** 流水产生之后系统账户的可用余额 */
    private BigDecimal balance;
    /** 流水产生之后系统账户的冻结余额 */
    private BigDecimal frozenAmount;
    /** 对应的系统账户的id */
    private Long systemAccountId;
}
