package com.zzx.p2p.base.controller;

import com.zzx.p2p.base.query.IpLogQueryObject;
import com.zzx.p2p.base.service.IpLogService;
import com.zzx.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录日志
 *
 * @author zzx
 * @date 2021-02-07 13:25:09
 */
@Controller
public class IpLogController {
    @Autowired
    private IpLogService ipLogService;

    /**
     * 个人用户登录日志列表
     *
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("/ipLog")
    public String ipLogList(@ModelAttribute("qo") IpLogQueryObject qo, Model model) {
        // 只查询自己的登录记录
        qo.setUsername(UserContext.getCurrent().getUsername());
        model.addAttribute("pageResult", ipLogService.query(qo));
        return "iplog_list";
    }
}
