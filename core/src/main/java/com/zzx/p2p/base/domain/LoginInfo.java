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
    public static final int STATE_NORMAL = 0;
    /** 锁定用户 */
    public static final int STATE_LOCKED = 0;

    private String username;
    private String password;
    private int state;
}
