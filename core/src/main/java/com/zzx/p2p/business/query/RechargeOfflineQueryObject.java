package com.zzx.p2p.business.query;

import com.zzx.p2p.base.query.AuditQueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * 线下充值查询
 *
 * @author zzx
 * @date 2021-02-23 17:32:14
 */
@Getter
@Setter
public class RechargeOfflineQueryObject extends AuditQueryObject {
    private Long applierId;
}
