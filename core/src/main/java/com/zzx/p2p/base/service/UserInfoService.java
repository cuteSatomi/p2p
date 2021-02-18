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
     *
     * @param userInfo
     */
    void update(UserInfo userInfo);

    /**
     * 添加userInfo
     *
     * @param userInfo
     */
    void add(UserInfo userInfo);

    /**
     * 根据id查询userInfo对象
     *
     * @param id
     * @return
     */
    UserInfo get(Long id);

    /**
     * 给用户绑定手机号
     *
     * @param phoneNumber
     * @param verifyCode
     */
    void bindPhone(String phoneNumber, String verifyCode);

    /**
     * 给用户的邮箱发送绑定的邮件
     *
     * @param email
     */
    void sendVerifyEmail(String email);

    /**
     * 绑定邮箱
     *
     * @param key
     */
    void bindEmail(String key);

    /**
     * 更新用户基本信息
     * @param userInfo
     */
    void updateBasicInfo(UserInfo userInfo);
}
