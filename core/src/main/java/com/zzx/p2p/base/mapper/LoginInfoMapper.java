package com.zzx.p2p.base.mapper;

import com.zzx.p2p.base.domain.LoginInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginInfoMapper {
    int insert(LoginInfo record);

    LoginInfo selectByPrimaryKey(Long id);

    List<LoginInfo> selectAll();

    int updateByPrimaryKey(LoginInfo record);

    /**
     * 根据用户名查询用户数量
     *
     * @param username
     * @return
     */
    int getCountByUsername(String username);

    /**
     * 登录
     * @param username
     * @param encode
     * @return
     */
    LoginInfo login(@Param("username") String username, @Param("password") String encode);
}