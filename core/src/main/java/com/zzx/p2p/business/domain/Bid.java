package com.zzx.p2p.business.domain;

import com.zzx.p2p.base.domain.BaseDomain;
import com.zzx.p2p.base.domain.LoginInfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 一次投标对象
 */
@Getter
@Setter
public class Bid extends BaseDomain {
    /** 年化利率（等同于BidRequest上的currentRate） */
    private BigDecimal actualRate;
    /** 这次投标金额 */
    private BigDecimal availableAmount;
    /** 关联借款 */
    private Long bidRequestId;
    /** 冗余数据，等同于借款标题 */
    private String bidRequestTitle;
    /** 投标人 */
    private LoginInfo bidUser;
    /** 投标时间 */
    private Date bidTime;
    /** 冗余数据，等同于借款状态 */
    private int bidRequestState;
}