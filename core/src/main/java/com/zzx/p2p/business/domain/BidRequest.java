package com.zzx.p2p.business.domain;

import com.alibaba.fastjson.JSONObject;
import com.zzx.p2p.base.domain.BaseDomain;
import com.zzx.p2p.base.domain.LoginInfo;
import com.zzx.p2p.business.util.DecimalFormatUtil;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zzx.p2p.base.util.BidConst.*;

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
    private BigDecimal currentSum = ZERO;
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

    /**
     * 计算投标完成还需金额
     *
     * @return
     */
    public BigDecimal getRemainAmount() {
        return DecimalFormatUtil.formatBigDecimal(bidRequestAmount.subtract(currentSum), DISPLAY_SCALE);
    }

    /**
     * 计算当前投标进度
     *
     * @return
     */
    public BigDecimal getPercent() {
        return currentSum.divide(bidRequestAmount, DISPLAY_SCALE, RoundingMode.HALF_UP);
    }

    public String getBidRequestStateDisplay() {
        switch (bidRequestState) {
            case BID_REQUEST_STATE_PUBLISH_PENDING:
                return "待发布";
            case BID_REQUEST_STATE_BIDDING:
                return "招标中";
            case BID_REQUEST_STATE_UNDO:
                return "已撤销";
            case BID_REQUEST_STATE_BIDDING_OVERDUE:
                return "流标";
            case BID_REQUEST_STATE_APPROVE_PENDING_1:
                return "满标一审";
            case BID_REQUEST_STATE_APPROVE_PENDING_2:
                return "满标二审";
            case BID_REQUEST_STATE_REJECTED:
                return "满标审核被拒";
            case BID_REQUEST_STATE_PAYING_BACK:
                return "还款中";
            case BID_REQUEST_STATE_COMPLETE_PAY_BACK:
                return "完成";
            case BID_REQUEST_STATE_PAY_BACK_OVERDUE:
                return "逾期";
            case BID_REQUEST_STATE_PUBLISH_REFUSE:
                return "发标拒绝";
            default:
                return "";
        }
    }

    public String getReturnTypeDisplay() {
        return returnType == RETURN_TYPE_MONTH_INTEREST ? "按月到期" : "等额本息";
    }

    /**
     * 返回当前的json字符串
     *
     * @return
     */
    public String getJsonString() {
        Map<String, Object> json = new HashMap<String, Object>(8);
        json.put("id", id);
        json.put("username", createUser.getUsername());
        json.put("title", title);
        json.put("bidRequestAmount", bidRequestAmount);
        json.put("currentRate", currentRate);
        json.put("months2Return", months2Return);
        json.put("returnType", getReturnTypeDisplay());
        json.put("totalRewardAmount", totalRewardAmount);
        return JSONObject.toJSONString(json);
    }
}