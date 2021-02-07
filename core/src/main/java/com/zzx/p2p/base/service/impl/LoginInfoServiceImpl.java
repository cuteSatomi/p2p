package com.zzx.p2p.base.service.impl;

import com.zzx.p2p.base.domain.Account;
import com.zzx.p2p.base.domain.IpLog;
import com.zzx.p2p.base.domain.LoginInfo;
import com.zzx.p2p.base.domain.UserInfo;
import com.zzx.p2p.base.mapper.IpLogMapper;
import com.zzx.p2p.base.mapper.LoginInfoMapper;
import com.zzx.p2p.base.service.AccountService;
import com.zzx.p2p.base.service.LoginInfoService;
import com.zzx.p2p.base.service.UserInfoService;
import com.zzx.p2p.base.util.MD5;
import com.zzx.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zzx
 * @date 2021-02-04 11:33:30
 */
@Service
public class LoginInfoServiceImpl implements LoginInfoService {
    @Autowired
    private LoginInfoMapper loginInfoMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private IpLogMapper ipLogMapper;

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

            // 初始化账户信息和userInfo
            Account account = new Account();
            // account的id就是loginInfo的id，这也是没有给account表的id字段设置主键自增的原因，因为loginInfo和account表就通过主键进行一对一关联
            account.setId(loginInfo.getId());
            accountService.add(account);

            UserInfo userInfo = new UserInfo();
            // userInfo的id也就是loginInfo的id，原因同上
            userInfo.setId(loginInfo.getId());
            userInfoService.add(userInfo);
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
    public LoginInfo login(String username, String password, String ip) {
        LoginInfo current = loginInfoMapper.login(username, MD5.encode(password));
        IpLog ipLog = new IpLog();
        ipLog.setIp(ip);
        ipLog.setLoginTime(new Date());
        ipLog.setUsername(username);
        if (current != null) {
            // 放到UserContext中
            UserContext.putCurrent(current);
            ipLog.setState(IpLog.LOGIN_SUCCESS);
        } else {
            ipLog.setState(IpLog.LOGIN_FAILED);
        }
        ipLogMapper.insert(ipLog);
        return current;
    }
}
