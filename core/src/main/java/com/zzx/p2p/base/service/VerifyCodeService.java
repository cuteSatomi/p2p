package com.zzx.p2p.base.service;

/**
 * 手机验证码相关服务
 *
 * @author zzx
 * @date 2021-02-08 16:33:10
 */
public interface VerifyCodeService {
    /**
     * 给指定的手机发送验证码
     *
     * @param phoneNumber
     */
    void sendVerifyCode(String phoneNumber);

    /**
     * 验证手机验证码
     *
     * @param phoneNumber
     * @param verifyCode
     * @return
     */
    boolean verify(String phoneNumber, String verifyCode);
}
