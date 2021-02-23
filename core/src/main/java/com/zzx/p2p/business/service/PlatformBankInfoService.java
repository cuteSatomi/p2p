package com.zzx.p2p.business.service;

import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.business.domain.PlatformBankInfo;
import com.zzx.p2p.business.query.PlatformBankInfoQueryObject;

import java.util.List;

/**
 * 平台账户服务
 *
 * @author zzx
 * @date 2021-02-23 13:45:10
 */
public interface PlatformBankInfoService {
    PageResult query(PlatformBankInfoQueryObject qo);

    void saveOrUpdate(PlatformBankInfo platformBankInfo);

    List<PlatformBankInfo> listAll();
}
