package com.zzx.p2p.business.mapper;

import com.zzx.p2p.business.domain.SystemAccount;

public interface SystemAccountMapper {
    int insert(SystemAccount record);

    int updateByPrimaryKey(SystemAccount record);

    /**
     * 返回当前活动的那个系统账户
     *
     * @return
     */
    SystemAccount selectCurrent();
}