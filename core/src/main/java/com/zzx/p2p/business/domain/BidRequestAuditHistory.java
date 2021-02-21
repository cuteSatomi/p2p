package com.zzx.p2p.business.domain;

import com.zzx.p2p.base.domain.BaseAuditDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * 借款审核历史对象
 *
 * @author zzx
 * @date 2021-02-21 13:09
 */
@Getter
@Setter
public class BidRequestAuditHistory extends BaseAuditDomain {
    /** 发标前审核 */
    public static final int PUBLISH_AUDIT = 0;
    /** 满标一审 */
    public static final int FULL_AUDIT_1 = 1;
    /** 满标二审 */
    public static final int FULL_AUDIT_2 = 2;

    private Long bidRequestId;
    private int auditType;

    public String getAuditTypeDisplay() {
        switch (auditType) {
            case PUBLISH_AUDIT:
                return "发标前审核";
            case FULL_AUDIT_1:
                return "满标一审";
            case FULL_AUDIT_2:
                return "满标二审";
            default:
                return "";
        }
    }
}
