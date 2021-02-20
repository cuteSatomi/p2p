package com.zzx.p2p.mgrsite.base.controller;

import com.zzx.p2p.base.query.UserFileQueryObject;
import com.zzx.p2p.base.service.UserFileService;
import com.zzx.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zzx
 * @date 2021-02-20 10:30:00
 */
@Controller
public class UserFileController {
    @Autowired
    private UserFileService userFileService;

    @RequestMapping("/userFileAuth")
    public String userFileAuth(@ModelAttribute("qo") UserFileQueryObject qo, Model model) {
        model.addAttribute("pageResult", userFileService.query(qo));
        return "userFileAuth/list";
    }

    @RequestMapping("/userFile_audit")
    @ResponseBody
    public JSONResult audit(Long id, int score, String remark, int state) {
        userFileService.audit(id, score, remark, state);
        return new JSONResult();
    }
}
