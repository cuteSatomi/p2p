package com.zzx.p2p.base.controller;

import com.zzx.p2p.base.domain.LoginInfo;
import com.zzx.p2p.base.service.AccountService;
import com.zzx.p2p.base.service.UserInfoService;
import com.zzx.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zzx
 * @date 2021-02-06 14:58
 */
@Controller
public class PersonalController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AccountService accountService;

    @RequestMapping("/personal")
    public String personalCenter(Model model){
        LoginInfo current = UserContext.getCurrent();
        model.addAttribute("userInfo",userInfoService.get(current.getId()));
        model.addAttribute("account",accountService.get(current.getId()));
        return "personal";
    }
}
