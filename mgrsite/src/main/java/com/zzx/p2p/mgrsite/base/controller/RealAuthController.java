package com.zzx.p2p.mgrsite.base.controller;

import com.zzx.p2p.base.query.RealAuthQueryObject;
import com.zzx.p2p.base.service.RealAuthService;
import com.zzx.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zzx
 * @date 2021-02-18 16:32
 */
@Controller
public class RealAuthController {
    @Autowired
    private RealAuthService realAuthService;

    @RequestMapping("/realAuth")
    public String realAuth(@ModelAttribute("qo") RealAuthQueryObject qo, Model model) {
        model.addAttribute("pageResult", realAuthService.query(qo));
        return "/realAuth/list";
    }

    @RequestMapping("/realAuth_audit")
    @ResponseBody
    public JSONResult realAuthAudit(Long id, String remark, int state) {
        realAuthService.audit(id, remark, state);
        return new JSONResult();
    }
}
