package com.zzx.p2p.base.controller;

import com.zzx.p2p.base.domain.LoginInfo;
import com.zzx.p2p.base.service.AccountService;
import com.zzx.p2p.base.service.UserInfoService;
import com.zzx.p2p.base.util.BidConst;
import com.zzx.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zzx
 * @date 2021-02-14 15:06
 */
@Controller
public class BorrowController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 到我要借款页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/borrow")
    public String borrowIndex(Model model) {
        LoginInfo current = UserContext.getCurrent();
        if (current == null) {
            // 如果未登陆则重定向到一个静态页面
            return "redirect:borrow.html";
        } else {
            model.addAttribute("account", accountService.get(current.getId()));
            model.addAttribute("userInfo",userInfoService.get(current.getId()));
            model.addAttribute("creditBorrowScore", BidConst.BASE_BORROW_SCORE);
            return "borrow";
        }
    }
}
