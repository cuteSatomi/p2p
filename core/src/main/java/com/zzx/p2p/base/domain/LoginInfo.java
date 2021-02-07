package com.zzx.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zzx
 * @date 2021-02-03 17:11:58
 */
@Getter
@Setter
public class LoginInfo extends BaseDomain{
    /** 正常用户 */
    public static final int STATE_NORMAL = 1;
    /** 锁定用户 */
    public static final int STATE_LOCKED = 0;
    /** 后台用户 */
    public static final int USER_MANAGER = 0;
    /** 前台用户 */
    public static final int USER_CLIENT = 1;

    private String username;
    private String password;
    private int state;

    private int userType;
}
