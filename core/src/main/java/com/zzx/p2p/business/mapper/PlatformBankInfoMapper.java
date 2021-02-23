package com.zzx.p2p.business.mapper;

import com.zzx.p2p.business.domain.PlatformBankInfo;
import com.zzx.p2p.business.query.PlatformBankInfoQueryObject;

import java.util.List;

public interface PlatformBankInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PlatformBankInfo record);

    PlatformBankInfo selectByPrimaryKey(Long id);

    List<PlatformBankInfo> selectAll();

    int updateByPrimaryKey(PlatformBankInfo record);

    /**
     * 分页查询相关
     *
     * @param qo
     * @return
     */
    int queryForCount(PlatformBankInfoQueryObject qo);

    List<PlatformBankInfo> query(PlatformBankInfoQueryObject qo);
}