package com.zzx.p2p.mgrsite.base.controller;

import com.zzx.p2p.base.query.IpLogQueryObject;
import com.zzx.p2p.base.service.IpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台查询登录日志
 *
 * @author zzx
 * @date 2021-02-08 10:23:28
 */
@Controller
public class IpLogController {
    @Autowired
    private IpLogService ipLogService;

    @RequestMapping("/ipLog")
    public String ipLogList(@ModelAttribute("qo") IpLogQueryObject qo, Model model) {
        model.addAttribute("pageResult", ipLogService.query(qo));
        return "ipLog/list";
    }
}
