package com.zzx.p2p.base.util;

/**
 * 用户状态类，记录用户在平台使用系统中所有的状态。
 *
 * @author Administrator
 */
public class BitStatesUtils {
    /** 用户绑定手机状态码 */
    public final static Long OP_BIND_PHONE = 1L;
    /** 用户绑定邮箱状态码 */
    public final static Long OP_BIND_EMAIL = 2L;
    /** 用户实名认证状态码 */
    public final static Long OP_REAL_AUTH = 4L;
    /** 用户是否是vip状态码 */
    public final static Long OP_VIP = 8L;

    /**
     * @param states 所有状态值
     * @param value  需要判断状态值
     * @return 是否存在
     */
    public static boolean hasState(long states, long value) {
        return (states & value) != 0;
    }

    /**
     * @param states 已有状态值
     * @param value  需要添加状态值
     * @return 新的状态值
     */
    public static long addState(long states, long value) {
        if (hasState(states, value)) {
            return states;
        }
        return (states | value);
    }

    /**
     * @param states 已有状态值
     * @param value  需要删除状态值
     * @return 新的状态值
     */
    public static long removeState(long states, long value) {
        if (!hasState(states, value)) {
            return states;
        }
        return states ^ value;
    }
}
