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
    /** 要查询的多个借款状态 */
    private int[] bidRequestStates;
    /** 根据哪一列进行排序 */
    private String orderBy;
    /** 升序或者是降序排列 */
    private String orderType;
}
