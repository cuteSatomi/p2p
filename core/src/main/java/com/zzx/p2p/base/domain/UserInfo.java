package com.zzx.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户相关信息
 *
 * @author zzx
 * @date 2021-02-05 9:59:51
 */
@Getter
@Setter
public class UserInfo extends BaseDomain {
    /** 版本号 */
    private int version;
    /** 状态码 */
    private long bitState;
    private String realName;
    private String idNumber;
    private String phoneNumber;
    /** 收入 */
    private SystemDictionaryItem incomeGrade;
    /** 婚姻情况 */
    private SystemDictionaryItem marriage;
    /** 子女情况 */
    private SystemDictionaryItem kidCount;
    /** 学历 */
    private SystemDictionaryItem educationBackground;
    /** 住房条件 */
    private SystemDictionaryItem houseCondition;
}
