package com.zzx.p2p.business.mapper;

import com.zzx.p2p.business.domain.BidRequestAuditHistory;

import java.util.List;

public interface BidRequestAuditHistoryMapper {
    int insert(BidRequestAuditHistory record);

    BidRequestAuditHistory selectByPrimaryKey(Long id);

    /**
     * 根据一个标查询出该标的所有审核历史
     * @param id
     * @return
     */
    List<BidRequestAuditHistory> listByBidRequest(Long id);
}