package com.zzx.p2p.business.domain;

import com.alibaba.fastjson.JSONObject;
import com.zzx.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 平台账号
 *
 * @author zzx
 * @date 2021-02-23 13:26:56
 */
@Getter
@Setter
public class PlatformBankInfo extends BaseDomain {
    /** 银行名称 */
    private String bankName;
    /** 开户人姓名 */
    private String accountName;
    /** 银行账号 */
    private String accountNumber;
    /** 开户支行 */
    private String bankForkName;

    /**
     * 返回当前的json字符串
     *
     * @return
     */
    public String getJsonString() {
        Map<String, Object> json = new HashMap<String, Object>(8);
        json.put("id", id);
        json.put("bankName",bankName);
        json.put("accountName",accountName);
        json.put("accountNumber",accountNumber);
        json.put("bankForkName",bankForkName);
        return JSONObject.toJSONString(json);
    }
}
