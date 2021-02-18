package com.zzx.p2p.base.service.impl;

import com.zzx.p2p.base.domain.MailVerify;
import com.zzx.p2p.base.domain.UserInfo;
import com.zzx.p2p.base.mapper.MailVerifyMapper;
import com.zzx.p2p.base.mapper.UserInfoMapper;
import com.zzx.p2p.base.service.UserInfoService;
import com.zzx.p2p.base.service.VerifyCodeService;
import com.zzx.p2p.base.util.BidConst;
import com.zzx.p2p.base.util.BitStatesUtils;
import com.zzx.p2p.base.util.DateUtil;
import com.zzx.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author zzx
 * @date 2021-02-05 11:36:03
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private MailVerifyMapper mailVerifyMapper;

    @Override
    public void update(UserInfo userInfo) {
        int ret = userInfoMapper.updateByPrimaryKey(userInfo);
        if (ret == 0) {
            throw new RuntimeException("乐观锁失败，UserInfo: " + userInfo.getId());
        }
    }

    @Override
    public void add(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    @Override
    public UserInfo get(Long id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void bindPhone(String phoneNumber, String verifyCode) {
        // 如果用户没有绑定手机号
        UserInfo current = get(UserContext.getCurrent().getId());
        if (!current.getIsBindPhone()) {
            // 验证验证码合法
            boolean flag = verifyCodeService.verify(phoneNumber, verifyCode);
            // 如果合法绑定手机
            if (flag) {
                current.setPhoneNumber(phoneNumber);
                current.addState(BitStatesUtils.OP_BIND_PHONE);
                update(current);
            } else {
                // 抛出异常，之前这里没有写在else里，导致执行到这里一定回抛异常，所以一直报错，真是操了
                throw new RuntimeException("绑定手机失败!");
            }
        }
    }

    @Override
    public void sendVerifyEmail(String email) {
        // 当前用户没有绑定邮箱
        UserInfo current = get(UserContext.getCurrent().getId());
        if (!current.getIsBindEmail()) {
            String uuid = UUID.randomUUID().toString();
            // 构造一份要发送的邮件
            StringBuilder content = new StringBuilder(100)
                    .append("点击<a href='http://localhost:8080/bindEmail.do?key=").append(uuid)
                    .append("'>这里</a>完成邮箱绑定，有效期为").append(BidConst.VERIFY_EMAIL_VALIDATE_DAY).append("天");

            try {
                System.out.println("给邮箱：" + email + "发送邮件" + content);
                MailVerify mv = new MailVerify();
                mv.setEmail(email);
                mv.setSendDate(new Date());
                mv.setUuid(uuid);
                mv.setUserInfoId(current.getId());
                mailVerifyMapper.insert(mv);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("发送验证邮箱失败!");
            }
        }
    }

    @Override
    public void bindEmail(String key) {
        // 根据uuid查询MailVerify对象
        MailVerify mv = mailVerifyMapper.selectByUUID(key);
        if (mv != null) {
            UserInfo current = get(mv.getUserInfoId());
            // 如果该用户还没有绑定邮箱，而且验证邮件还在有效期内，则执行绑定
            if (!current.getIsBindEmail() &&
                    DateUtil.secondsBetween(new Date(), mv.getSendDate()) <= BidConst.VERIFY_EMAIL_VALIDATE_DAY * 24 * 3600) {
                current.addState(BitStatesUtils.OP_BIND_EMAIL);
                current.setEmail(mv.getEmail());
                update(current);
                return;
            }
        }
        throw new RuntimeException("绑定邮箱失败!");
    }

    @Override
    public void updateBasicInfo(UserInfo userInfo) {
        // 查出原来的UserInfo对象
        UserInfo old = get(UserContext.getCurrent().getId());

        // 设置基本信息
        old.setEducationBackground(userInfo.getEducationBackground());
        old.setHouseCondition(userInfo.getHouseCondition());
        old.setKidCount(userInfo.getKidCount());
        old.setMarriage(userInfo.getMarriage());
        old.setIncomeGrade(userInfo.getIncomeGrade());

        // 设置状态码
        if(!old.getIsBasicInfo()){
            old.addState(BitStatesUtils.OP_BASIC_INFO);
        }

        // 执行更新
        update(old);
    }
}
