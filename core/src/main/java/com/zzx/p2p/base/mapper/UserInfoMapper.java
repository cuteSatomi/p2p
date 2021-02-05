package com.zzx.p2p.base.mapper;

import com.zzx.p2p.base.domain.UserInfo;

public interface UserInfoMapper {
    int insert(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKey(UserInfo record);
}