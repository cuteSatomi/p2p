package com.zzx.p2p.base.service;

import com.zzx.p2p.base.domain.UserInfo;

/**
 * 用户相关服务
 *
 * @author zzx
 * @date 2021-02-05 11:35:57
 */
public interface UserInfoService {
    /**
     * 乐观锁支持
     * @param userInfo
     */
    void update(UserInfo userInfo);
}
