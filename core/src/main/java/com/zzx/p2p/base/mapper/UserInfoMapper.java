package com.zzx.p2p.base.mapper;

import com.zzx.p2p.base.domain.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserInfoMapper {
    int insert(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKey(UserInfo record);

    List<Map<String, Object>> autocomplete(String keyword);
}