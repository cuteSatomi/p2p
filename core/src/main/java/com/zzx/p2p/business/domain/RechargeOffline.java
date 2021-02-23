package com.zzx.p2p.business.domain;

import com.alibaba.fastjson.JSONObject;
import com.zzx.p2p.base.domain.BaseAuditDomain;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 返回当前的json字符串
     *
     * @return
     */
    public String getJsonString() {
        Map<String, Object> json = new HashMap<String, Object>(8);
        json.put("id", id);
        json.put("username", applier.getUsername());
        json.put("tradeCode", tradeCode);
        json.put("amount", amount);
        json.put("tradeTime", tradeTime);
        return JSONObject.toJSONString(json);
    }
}
