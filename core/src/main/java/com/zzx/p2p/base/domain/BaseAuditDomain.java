package com.zzx.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 基础的审核对象
 *
 * @author zzx
 * @date 2021-02-19 10:15:25
 */
@Getter
@Setter
abstract public class BaseAuditDomain extends BaseDomain {

    /** 正常 */
    public static final int STATE_NORMAL = 0;
    /** 审核通过 */
    public static final int STATE_AUDIT = 1;
    /** 审核拒绝 */
    public static final int STATE_REJECT = 2;

    /** 申请人 */
    protected LoginInfo applier;
    /** 审核人 */
    protected LoginInfo auditor;
    /** 审核备注 */
    protected String remark;
    /** 状态 */
    protected int state;
    /** 申请时间 */
    protected Date applyTime;
    /** 审核时间 */
    protected Date auditTime;

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
}
