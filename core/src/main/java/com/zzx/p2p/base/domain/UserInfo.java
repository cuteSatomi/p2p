package com.zzx.p2p.base.domain;

import com.zzx.p2p.base.util.BitStatesUtils;
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
    private long bitState = 0;
    private String realName;
    private String idNumber;
    private String phoneNumber;
    private String email;
    /** 风控累计分数 */
    private int score;
    /** 该用户对应的实名认证对象的id */
    private Long realAuthId;
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

    /** 判断是否已经绑定了手机 */
    public boolean getIsBindPhone() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_BIND_PHONE);
    }

    /** 判断是否已经绑定了邮箱 */
    public boolean getIsBindEmail() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_BIND_EMAIL);
    }

    /** 判断是否已经填写基本资料 */
    public boolean getIsBasicInfo() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_BASIC_INFO);
    }

    /** 判断用户是否已经实名认证 */
    public boolean getIsRealAuth() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_REAL_AUTH);
    }

    /** 判断用户是否已经视频认证 */
    public boolean getIsVideoAuth() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_VIDEO_AUTH);
    }

    /** 添加绑定的状态码 */
    public void addState(Long state) {
        bitState = BitStatesUtils.addState(bitState, state);
    }

    /** 移除状态码 */
    public void removeState(Long state) {
        bitState = BitStatesUtils.removeState(this.bitState, state);
    }
}
