package com.zzx.p2p.base.service;

import com.zzx.p2p.base.query.IpLogQueryObject;
import com.zzx.p2p.base.query.PageResult;

/**
 * @author zzx
 * @date 2021-02-07 11:53:57
 */
public interface IpLogService {
    /**
     * 分页查询登录信息
     *
     * @param qo
     * @return
     */
    PageResult query(IpLogQueryObject qo);
}
