package com.zzx.p2p.base.service.impl;

import com.zzx.p2p.base.domain.LoginInfo;
import com.zzx.p2p.base.mapper.LoginInfoMapper;
import com.zzx.p2p.base.service.LoginInfoService;
import com.zzx.p2p.base.util.MD5;
import com.zzx.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zzx
 * @date 2021-02-04 11:33:30
 */
@Service
public class LoginInfoServiceImpl implements LoginInfoService {
    @Autowired
    private LoginInfoMapper loginInfoMapper;

    @Override
    public void register(String username, String password) {
        int count = loginInfoMapper.getCountByUsername(username);
        if (count <= 0) {
            // 用户不存在则创建并保存到数据库
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUsername(username);
            loginInfo.setPassword(MD5.encode(password));
            loginInfo.setState(LoginInfo.STATE_NORMAL);
            loginInfoMapper.insert(loginInfo);
        } else {
            // 用户已存在则抛出异常
            throw new RuntimeException("该用户名已存在");
        }
    }

    @Override
    public boolean checkUsername(String username) {
        return loginInfoMapper.getCountByUsername(username) > 0;
    }

    @Override
    public void login(String username, String password) {
        LoginInfo current = loginInfoMapper.login(username, MD5.encode(password));
        if (current != null) {
            // 放到UserContext中
            UserContext.putCurrent(current);
        } else {
            throw new RuntimeException("用户名或密码错误！");
        }
    }
}
