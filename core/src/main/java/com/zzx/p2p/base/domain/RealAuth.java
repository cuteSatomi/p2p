package com.zzx.p2p.base.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证对象
 *
 * @author zzx
 * @date 2021-02-17 20:25
 */
@Getter
@Setter
public class RealAuth extends BaseDomain {
    public static final int SEX_MALE = 1;
    public static final int SEX_FEMALE = 0;

    /** 正常 */
    public static final int STATE_NORMAL = 0;
    /** 审核通过 */
    public static final int STATE_AUDIT = 1;
    /** 审核拒绝 */
    public static final int STATE_REJECT = 2;

    private String realName;
    private int sex;
    private String idNumber;
    private String bornDate;
    /** 证件地址 */
    private String address;
    /** 身份证照片正面地址 */
    private String image1;
    /** 身份证照片背面地址 */
    private String image2;


    /** 申请人 */
    private LoginInfo applier;
    /** 审核人 */
    private LoginInfo auditor;
    /** 审核备注 */
    private String remark;
    /** 状态 */
    private int state;
    /** 申请时间 */
    private Date applyTime;
    /** 审核时间 */
    private Date auditTime;

    public String getSexDisplay() {
        return sex == SEX_MALE ? "男" : "女";
    }

    public String getStateDisplay() {
        switch (state) {
            case STATE_NORMAL:
                return "待审核";
            case STATE_AUDIT:
                return "审核通过";
            case STATE_REJECT:
                return "审核拒绝";
            default:
                return "";
        }
    }

    /**
     * 返回当前的json字符串
     *
     * @return
     */
    public String getJsonString() {
        Map<String, Object> json = new HashMap<String, Object>(8);
        json.put("id", id);
        json.put("applier", applier.getUsername());
        json.put("realName", realName);
        json.put("idNumber", idNumber);
        json.put("sex", getSexDisplay());
        json.put("bornDate", bornDate);
        json.put("address", address);
        json.put("image1", image1);
        json.put("image2", image2);
        return JSONObject.toJSONString(json);
    }
}
