package com.zzx.p2p.base.util;

import java.math.BigDecimal;

/**
 * 系统中的常量
 *
 * @author zzx
 * @date 2021-02-05 10:19:25
 */
public class BidConst {
    /** 存储精度 */
    public static final int STORE_SCALE = 4;
    /** 运算精度 */
    public static final int CAL_SCALE = 8;
    /** 显示精度 */
    public static final int DISPLAY_SCALE = 2;
    /** 系统级别的"0" */
    public static final BigDecimal ZERO = new BigDecimal("0.0000");
    /** 初始授信额度 */
    public static final BigDecimal INIT_BORROW_LIMIT = new BigDecimal("6000.0000");
}
