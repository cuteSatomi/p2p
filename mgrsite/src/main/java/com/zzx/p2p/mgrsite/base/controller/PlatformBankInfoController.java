package com.zzx.p2p.mgrsite.base.controller;

import com.zzx.p2p.business.domain.PlatformBankInfo;
import com.zzx.p2p.business.query.PlatformBankInfoQueryObject;
import com.zzx.p2p.business.service.PlatformBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 平台账户管理
 *
 * @author zzx
 * @date 2021-02-23 13:54:46
 */
@Controller
public class PlatformBankInfoController {
    @Autowired
    private PlatformBankInfoService platformBankInfoService;

    @RequestMapping("/companyBank_list")
    public String PlatformBankInfoList(@ModelAttribute("qo") PlatformBankInfoQueryObject qo, Model model) {
        model.addAttribute("pageResult", platformBankInfoService.query(qo));
        return "platformbankinfo/list";
    }

    /**
     * 修改或保存
     *
     * @param platformBankInfo
     * @return
     */
    @RequestMapping("/companyBank_update")
    public String update(PlatformBankInfo platformBankInfo) {
        platformBankInfoService.saveOrUpdate(platformBankInfo);
        return "redirect:/companyBank_list.do";
    }
}
