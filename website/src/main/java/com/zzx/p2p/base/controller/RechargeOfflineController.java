package com.zzx.p2p.base.controller;

import com.zzx.p2p.base.annotation.RequireLogin;
import com.zzx.p2p.base.util.JSONResult;
import com.zzx.p2p.base.util.UserContext;
import com.zzx.p2p.business.domain.RechargeOffline;
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
 * 前台的线下充值
 *
 * @author zzx
 * @date 2021-02-23 15:49:24
 */
@Controller
public class RechargeOfflineController {
    @Autowired
    private PlatformBankInfoService platformBankInfoService;

    @Autowired
    private RechargeOfflineService rechargeOfflineService;

    /**
     * 导向到线下充值页面
     *
     * @param model
     * @return
     */
    @RequireLogin
    @RequestMapping("/recharge")
    public String recharge(Model model) {
        model.addAttribute("banks", platformBankInfoService.listAll());
        return "recharge";
    }

    /**
     * 提交线下充值单
     *
     * @return
     */
    @RequireLogin
    @RequestMapping("/recharge_save")
    @ResponseBody
    public JSONResult rechargeApply(RechargeOffline recharge) {
        rechargeOfflineService.apply(recharge);
        return new JSONResult();
    }

    @RequireLogin
    @RequestMapping("/recharge_list")
    public String rechargeList(@ModelAttribute("qo") RechargeOfflineQueryObject qo, Model model) {
        qo.setApplierId(UserContext.getCurrent().getId());
        model.addAttribute("pageResult", rechargeOfflineService.query(qo));
        return "recharge_list";
    }
}
