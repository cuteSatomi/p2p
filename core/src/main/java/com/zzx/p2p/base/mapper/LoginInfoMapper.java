package com.zzx.p2p.base.mapper;

import com.zzx.p2p.base.domain.LoginInfo;
import java.util.List;

public interface LoginInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoginInfo record);

    LoginInfo selectByPrimaryKey(Long id);

    List<LoginInfo> selectAll();

    int updateByPrimaryKey(LoginInfo record);
}