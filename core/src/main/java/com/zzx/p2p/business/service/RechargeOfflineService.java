package com.zzx.p2p.business.service;

import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.business.domain.RechargeOffline;
import com.zzx.p2p.business.query.RechargeOfflineQueryObject;

/**
 * 线下充值服务
 *
 * @author zzx
 * @date 2021-02-23 16:00:19
 */
public interface RechargeOfflineService {
    /**
     * 提交线下充值单申请
     *
     * @param recharge
     */
    void apply(RechargeOffline recharge);

    PageResult query(RechargeOfflineQueryObject qo);

    /**
     * 线下充值审核
     *
     * @param id
     * @param remark
     * @param state
     */
    void audit(Long id, String remark, int state);
}
