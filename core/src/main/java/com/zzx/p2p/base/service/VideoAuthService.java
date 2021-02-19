package com.zzx.p2p.base.service;

import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.base.query.VideoAuthQueryObject;

/**
 * 视频认证服务
 *
 * @author zzx
 * @date 2021-02-19 14:25:38
 */
public interface VideoAuthService {
    PageResult query(VideoAuthQueryObject qo);

    /**
     * 视频审核
     * @param loginInfoValue
     * @param remark
     * @param state
     */
    void audit(Long loginInfoValue, String remark, int state);
}
