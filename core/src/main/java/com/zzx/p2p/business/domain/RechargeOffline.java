package com.zzx.p2p.business.domain;

import com.zzx.p2p.base.domain.BaseAuditDomain;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 线下充值单
 *
 * @author zzx
 * @date 2021-02-23 14:30:13
 */
@Getter
@Setter
public class RechargeOffline extends BaseAuditDomain {
    private PlatformBankInfo bankInfo;
    /** 交易号 */
    private String tradeCode;
    /** 充值时间 */
    private Date tradeTime;
    /** 充值金额 */
    private BigDecimal amount;
    /** 充值说明 */
    private String note;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }
}
