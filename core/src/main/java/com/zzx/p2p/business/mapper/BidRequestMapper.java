package com.zzx.p2p.business.mapper;

import com.zzx.p2p.business.domain.BidRequest;
import com.zzx.p2p.business.query.BidRequestQueryObject;

import java.util.List;

public interface BidRequestMapper {
    int insert(BidRequest record);

    BidRequest selectByPrimaryKey(Long id);

    int updateByPrimaryKey(BidRequest record);

    /**
     * 分页查询相关方法
     * @param qo
     * @return
     */
    int queryForCount(BidRequestQueryObject qo);
    List<BidRequest> query(BidRequestQueryObject qo);
}