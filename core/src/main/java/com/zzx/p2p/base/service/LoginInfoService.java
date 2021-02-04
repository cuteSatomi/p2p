package com.zzx.p2p.base.service;

/**
 * 登录相关服务
 *
 * @author zzx
 * @date 2021-02-04 11:28:55
 */
public interface LoginInfoService {

    /**
     * 用户注册
     * @param username
     * @param password
     */
    void register(String username, String password);

    /**
     * 检查用户名是否存在
     * @param username
     * @return 存在返回true，不存在返回false
     */
    boolean checkUsername(String username);

    void login(String username, String password);
}
