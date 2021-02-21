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

    /** 默认管理员的用户名 */
    public static final String DEFAULT_ADMIN_NAME = "admin";
    /** 默认管理员的密码 */
    public static final String DEFAULT_ADMIN_PASSWORD = "123";

    /** 手机验证码有效期 */
    public static final int VERIFY_CODE_VALIDATE_SECOND = 300;
    /** 邮箱验证的有效期 */
    public static final int VERIFY_EMAIL_VALIDATE_DAY = 5;

    /** 借款需要达到的最低风控分数 */
    public static final int BASE_BORROW_SCORE = 30;

    // --------------------还款类型---------------------------
    /** 还款方式:按月分期还款(等额本息) */
    public final static int RETURN_TYPE_MONTH_INTEREST_PRINCIPAL = 0;
    /** 还款方式:按月到期还款(每月还利息,到期还本息) */
    public final static int RETURN_TYPE_MONTH_INTEREST = 1;

    // ---------------------标的类型--------------------------
    /** 普通信用标 */
    public final static int BID_REQUEST_TYPE_NORMAL = 0;

    // ---------------------借款状态---------------------------
    /** 待发布 */
    public final static int BID_REQUEST_STATE_PUBLISH_PENDING = 0;
    /** 招标中 */
    public final static int BID_REQUEST_STATE_BIDDING = 1;
    /** 已撤销 */
    public final static int BID_REQUEST_STATE_UNDO = 2;
    /** 流标 */
    public final static int BID_REQUEST_STATE_BIDDING_OVERDUE = 3;
    /** 满标1审 */
    public final static int BID_REQUEST_STATE_APPROVE_PENDING_1 = 4;
    /** 满标2审 */
    public final static int BID_REQUEST_STATE_APPROVE_PENDING_2 = 5;
    /** 满标审核被拒绝 */
    public final static int BID_REQUEST_STATE_REJECTED = 6;
    /** 还款中 */
    public final static int BID_REQUEST_STATE_PAYING_BACK = 7;
    /** 已还清 */
    public final static int BID_REQUEST_STATE_COMPLETE_PAY_BACK = 8;
    /** 逾期 */
    public final static int BID_REQUEST_STATE_PAY_BACK_OVERDUE = 9;
    /** 发标审核拒绝状态 */
    public final static int BID_REQUEST_STATE_PUBLISH_REFUSE = 10;

    /** 系统规定的最小投标金额 */
    public static final BigDecimal SMALLEST_BID_AMOUNT = new BigDecimal("50.0000");
    /** 系统规定的最小借款金额 */
    public static final BigDecimal SMALLEST_BID_REQUEST_AMOUNT = new BigDecimal("500.0000");

    /** 系统最小借款利息 */
    public static final BigDecimal SMALLEST_CURRENT_RATE = new BigDecimal("5.0000");
    /** 系统最大借款利息 */
    public static final BigDecimal MAX_CURRENT_RATE = new BigDecimal("20.0000");
}
