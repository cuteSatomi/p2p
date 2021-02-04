package com.zzx.p2p.base.controller;

import com.zzx.p2p.base.service.LoginInfoService;
import com.zzx.p2p.base.util.JSONResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用于注册/登录相关
 *
 * @author zzx
 * @date 2021-02-04 11:47:40
 */
@Controller
public class RegisterController {
    @Autowired
    private LoginInfoService loginInfoService;

    @RequestMapping("/register")
    @ResponseBody
    public JSONResult register(String username, String password) {
        JSONResult result = new JSONResult();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            result.setSuccess(false);
            result.setMsg("用户名或者密码不能为空");
            return result;
        }

        try {
            loginInfoService.register(username, password);
        } catch (RuntimeException e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/checkUsername")
    @ResponseBody
    public Boolean checkUsername(String username) {
        return !loginInfoService.checkUsername(username);
    }

    @RequestMapping("/login")
    @ResponseBody
    public JSONResult login(String username, String password) {
        JSONResult result = new JSONResult();
        try {
            loginInfoService.login(username, password);
        } catch (RuntimeException e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
