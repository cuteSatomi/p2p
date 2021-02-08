package com.zzx.p2p.base.controller;

import com.zzx.p2p.base.service.VerifyCodeService;
import com.zzx.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zzx
 * @date 2021-02-08 16:19:56
 */
@Controller
public class VerifyCodeController {

    @Autowired
    private VerifyCodeService verifyCodeService;

    @RequestMapping("/sendVerifyCode")
    @ResponseBody
    public JSONResult sendVerifyCode(String phoneNumber) {
        JSONResult result = new JSONResult();
        try {
            verifyCodeService.sendVerifyCode(phoneNumber);
        } catch (RuntimeException e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
