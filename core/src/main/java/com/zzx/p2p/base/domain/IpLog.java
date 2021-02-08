package com.zzx.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 登录的日志类
 *
 * @author zzx
 * @date 2021-02-07 10:10:48
 */
@Getter
@Setter
public class IpLog extends BaseDomain {

    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAILED = 0;

    private String ip;
    private Date loginTime;
    private String username;
    private int state;
    /**
     * 用户登录类型
     */
    private int userType;

    public String getStateDisplay() {
        return state == LOGIN_SUCCESS ? "登录成功" : "登录失败";
    }

    public String getUserTypeDisplay() {
        return userType == LoginInfo.USER_CLIENT ? "前台用户" : "后台管理员";
    }
}
