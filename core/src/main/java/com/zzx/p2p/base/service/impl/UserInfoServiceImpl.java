package com.zzx.p2p.base.service.impl;

import com.zzx.p2p.base.domain.UserInfo;
import com.zzx.p2p.base.mapper.UserInfoMapper;
import com.zzx.p2p.base.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zzx
 * @date 2021-02-05 11:36:03
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void update(UserInfo userInfo) {
        int ret = userInfoMapper.updateByPrimaryKey(userInfo);
        if (ret == 0) {
            throw new RuntimeException("乐观锁失败，UserInfo: " + userInfo.getId());
        }
    }
}
