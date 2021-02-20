package com.zzx.p2p.business.domain;

import com.zzx.p2p.base.domain.BaseDomain;
import com.zzx.p2p.base.domain.LoginInfo;
import com.zzx.p2p.base.util.BidConst;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 借款对象
 */
@Getter
@Setter
public class BidRequest extends BaseDomain {
    /** 版本号 */
    private int version;
    /** 还款类型（等额本息） */
    private int returnType;
    /** 借款类型（信用标） */
    private int bidRequestType;
    /** 借款状态 */
    private int bidRequestState;
    /** 借款总金额 */
    private BigDecimal bidRequestAmount;
    /** 年化利率 */
    private BigDecimal currentRate;
    /** 最小借款金额 */
    private BigDecimal minBidAmount;
    /** 还款月数 */
    private int months2Return;
    /** 已投标次数（冗余） */
    private int bidCount = 0;
    /** 总回报金额 */
    private BigDecimal totalRewardAmount;
    /** 当前已投标总金额 */
    private BigDecimal currentSum = BidConst.ZERO;
    /** 借款标题 */
    private String title;
    /** 借款描述 */
    private String description;
    /** 风控意见 */
    private String note;
    /** 照表截止日期 */
    private Date disableDate;
    /** 招标天数 */
    private int disableDays;
    /** 借款人 */
    private LoginInfo createUser;
    /** 针对该借款的投标 */
    private List<Bid> bids;
    /** 申请时间 */
    private Date applyTime;
    /** 发标时间 */
    private Date publishTime;
}