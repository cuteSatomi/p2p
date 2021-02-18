package com.zzx.p2p.base.service;

import com.zzx.p2p.base.domain.RealAuth;
import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.base.query.RealAuthQueryObject;

/**
 * 实名认证相关服务
 *
 * @author zzx
 * @date 2021-02-17 22:05
 */
public interface RealAuthService {

    RealAuth get(Long id);

    /**
     * 实名认证申请
     *
     * @param realAuth
     */
    void apply(RealAuth realAuth);

    PageResult query(RealAuthQueryObject qo);

    /**
     * 实名认证审核
     * @param id
     * @param remark
     * @param state
     */
    void audit(Long id, String remark, int state);
}
