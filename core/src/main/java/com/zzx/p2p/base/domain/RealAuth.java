package com.zzx.p2p.base.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

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
public class RealAuth extends BaseAuditDomain {
    public static final int SEX_MALE = 1;
    public static final int SEX_FEMALE = 0;


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

    public String getSexDisplay() {
        return sex == SEX_MALE ? "男" : "女";
    }

    /**
     * 获取用户真实名字的隐藏字符串，只显示姓氏
     *
     * @return
     */
    public String getAnonymousRealName() {
        if (StringUtils.hasLength(this.realName)) {
            int len = realName.length();
            String replace = "";
            replace += realName.charAt(0);
            for (int i = 1; i < len; i++) {
                replace += "*";
            }
            return replace;
        }
        return realName;
    }

    /**
     * 获取用户身份号码的隐藏字符串
     *
     * @return
     */
    public String getAnonymousIdNumber() {
        if (StringUtils.hasLength(idNumber)) {
            int len = idNumber.length();
            String replace = "";
            for (int i = 0; i < len; i++) {
                if ((i > 5 && i < 10) || (i > len - 5)) {
                    replace += "*";
                } else {
                    replace += idNumber.charAt(i);
                }
            }
            return replace;
        }
        return idNumber;
    }

    /**
     * 获取用户住址的隐藏字符串
     *
     * @return
     */
    public String getAnonymousAddress() {
        if (StringUtils.hasLength(address) && address.length() > 4) {
            String last = address.substring(address.length() - 4,
                    address.length());
            String stars = "";
            for (int i = 0; i < address.length() - 4; i++) {
                stars += "*";
            }
            return stars + last;
        }
        return address;
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
