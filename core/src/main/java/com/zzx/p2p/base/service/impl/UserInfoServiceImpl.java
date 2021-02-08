package com.zzx.p2p.base.service.impl;

import com.zzx.p2p.base.domain.UserInfo;
import com.zzx.p2p.base.mapper.UserInfoMapper;
import com.zzx.p2p.base.service.UserInfoService;
import com.zzx.p2p.base.service.VerifyCodeService;
import com.zzx.p2p.base.util.BitStatesUtils;
import com.zzx.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                current.addState(BitStatesUtils.OP_BIND_PHONE);
                update(current);
            }
            // 抛出异常
            throw new RuntimeException("绑定手机失败!");
        }
    }
}
