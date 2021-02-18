package com.zzx.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 邮箱验证对象
 *
 * @author zzx
 * @date 2021-02-12 14:35
 */
@Getter
@Setter
public class MailVerify extends BaseDomain{
    private Long userInfoId;
    private String email;
    private String uuid;
    private Date sendDate;
}
