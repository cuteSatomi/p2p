package com.zzx.p2p.business.mapper;

import com.zzx.p2p.business.domain.RechargeOffline;
import com.zzx.p2p.business.query.RechargeOfflineQueryObject;

import java.util.List;

public interface RechargeOfflineMapper {
    int insert(RechargeOffline record);

    RechargeOffline selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RechargeOffline record);

    /**
     * 分页查询
     * @param qo
     * @return
     */
    int queryForCount(RechargeOfflineQueryObject qo);
    List<RechargeOffline> query(RechargeOfflineQueryObject qo);
}