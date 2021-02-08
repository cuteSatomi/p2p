package com.zzx.p2p.base.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 存放验证码相关内容，这个对象放在session中
 *
 * @author zzx
 * @date 2021-02-08 16:37:37
 */
@Getter
@Setter
public class VerifyCodeVO {
    /** 验证码 */
    private String verifyCode;
    /** 发送验证码的手机号 */
    private String phoneNumber;
    /** 最后成功发送的时间 */
    private Date lastSendTime;
}
