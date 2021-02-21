package com.zzx.p2p.business.query;

import com.zzx.p2p.base.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * 查询借款
 *
 * @author zzx
 * @date 2021-02-21 13:29
 */
@Getter
@Setter
public class BidRequestQueryObject extends QueryObject {
    /** 借款状态 */
    private int bidRequestState = -1;
}
