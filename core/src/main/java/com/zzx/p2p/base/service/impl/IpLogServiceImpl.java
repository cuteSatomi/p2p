package com.zzx.p2p.base.service.impl;

import com.zzx.p2p.base.domain.IpLog;
import com.zzx.p2p.base.mapper.IpLogMapper;
import com.zzx.p2p.base.query.IpLogQueryObject;
import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.base.service.IpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zzx
 * @date 2021-02-07 11:55:20
 */
@Service
public class IpLogServiceImpl implements IpLogService {
    @Autowired
    private IpLogMapper ipLogMapper;

    @Override
    public PageResult query(IpLogQueryObject qo) {
        int count = ipLogMapper.queryForCount(qo);
        if (count > 0) {
            List<IpLog> list = ipLogMapper.query(qo);
            return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }
}
