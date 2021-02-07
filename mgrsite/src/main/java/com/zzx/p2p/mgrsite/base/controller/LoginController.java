package com.zzx.p2p.mgrsite.base.controller;

import com.zzx.p2p.base.domain.LoginInfo;
import com.zzx.p2p.base.service.LoginInfoService;
import com.zzx.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台登录
 *
 * @author zzx
 * @date 2021-02-07 16:20:57
 */
@Controller
public class LoginController {
    @Autowired
    private LoginInfoService loginInfoService;

    /**
     * 后台登录
     *
     * @param username
     * @param password
     * @param request
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public JSONResult login(String username, String password, HttpServletRequest request) {
        JSONResult result = new JSONResult();
        LoginInfo current = loginInfoService.login(username, password, request.getRemoteAddr(), LoginInfo.USER_MANAGER);
        if (current == null) {
            result.setSuccess(false);
            result.setMsg("用户名或密码错误！");
        }
        return result;
    }

    /**
     * 后台首页
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "main";
    }
}
