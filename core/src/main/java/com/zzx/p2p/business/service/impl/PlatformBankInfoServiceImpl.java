package com.zzx.p2p.business.service.impl;

import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.business.domain.PlatformBankInfo;
import com.zzx.p2p.business.mapper.PlatformBankInfoMapper;
import com.zzx.p2p.business.query.PlatformBankInfoQueryObject;
import com.zzx.p2p.business.service.PlatformBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zzx
 * @date 2021-02-23 13:45:37
 */
@Service
public class PlatformBankInfoServiceImpl implements PlatformBankInfoService {
    @Autowired
    private PlatformBankInfoMapper platformBankInfoMapper;

    @Override
    public PageResult query(PlatformBankInfoQueryObject qo) {
        int count = platformBankInfoMapper.queryForCount(qo);
        if (count > 0) {
            List<PlatformBankInfo> list = platformBankInfoMapper.query(qo);
            return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void saveOrUpdate(PlatformBankInfo platformBankInfo) {
        if (platformBankInfo.getId() != null) {
            // id不是null就是修改
            platformBankInfoMapper.updateByPrimaryKey(platformBankInfo);
        } else {
            platformBankInfoMapper.insert(platformBankInfo);
        }
    }

    @Override
    public List<PlatformBankInfo> listAll() {
        return platformBankInfoMapper.selectAll();
    }
}
