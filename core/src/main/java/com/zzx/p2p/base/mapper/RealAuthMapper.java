package com.zzx.p2p.base.mapper;

import com.zzx.p2p.base.domain.RealAuth;
import com.zzx.p2p.base.query.RealAuthQueryObject;

import java.util.List;

public interface RealAuthMapper {
    int insert(RealAuth record);

    RealAuth selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RealAuth record);

    /**
     * 后台审核分页查询相关
     */
    int queryForCount(RealAuthQueryObject qo);
    List<RealAuth> query(RealAuthQueryObject qo);
}