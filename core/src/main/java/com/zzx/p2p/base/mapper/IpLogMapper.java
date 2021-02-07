package com.zzx.p2p.base.mapper;

import com.zzx.p2p.base.domain.IpLog;
import com.zzx.p2p.base.query.IpLogQueryObject;

import java.util.List;

public interface IpLogMapper {
    int insert(IpLog record);

    /**
     * 高级查询总数
     * @param qo
     * @return
     */
    int queryForCount(IpLogQueryObject qo);

    /**
     * 查询当前页数据
     * @param qo
     * @return
     */
    List<IpLog> query(IpLogQueryObject qo);
}