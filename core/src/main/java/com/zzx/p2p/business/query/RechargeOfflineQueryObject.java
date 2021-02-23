package com.zzx.p2p.business.query;

import com.zzx.p2p.base.query.AuditQueryObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

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
    /** 按照开户行查询 */
    private Long bankInfoId;
    /** 按照交易号查询 */
    private String tradeCode;

    public String getTradeCode() {
        return StringUtils.hasLength(tradeCode) ? tradeCode : null;
    }
}
