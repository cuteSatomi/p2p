package com.zzx.p2p.business.mapper;

import com.zzx.p2p.business.domain.Bid;

public interface BidMapper {
    int insert(Bid record);

    Bid selectByPrimaryKey(Long id);
}