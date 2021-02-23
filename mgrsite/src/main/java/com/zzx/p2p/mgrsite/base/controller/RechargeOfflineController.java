package com.zzx.p2p.mgrsite.base.controller;

import com.zzx.p2p.base.util.JSONResult;
import com.zzx.p2p.business.query.RechargeOfflineQueryObject;
import com.zzx.p2p.business.service.PlatformBankInfoService;
import com.zzx.p2p.business.service.RechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 线下充值审核
 *
 * @author zzx
 * @date 2021-02-23 20:23
 */
@Controller
public class RechargeOfflineController {
    @Autowired
    private RechargeOfflineService rechargeOfflineService;

    @Autowired
    private PlatformBankInfoService platformBankInfoService;

    @RequestMapping("/rechargeOffline")
    public String rechargeOffline(@ModelAttribute("qo") RechargeOfflineQueryObject qo, Model model) {
        model.addAttribute("pageResult", rechargeOfflineService.query(qo));
        model.addAttribute("banks", platformBankInfoService.listAll());
        return "rechargeoffline/list";
    }

    @RequestMapping("/rechargeOffline_audit")
    @ResponseBody
    public JSONResult audit(Long id, String remark, int state) {
        rechargeOfflineService.audit(id, remark, state);
        return new JSONResult();
    }
}
