package com.zzx.p2p.mgrsite.base.controller;

import com.zzx.p2p.base.query.VideoAuthQueryObject;
import com.zzx.p2p.base.service.UserInfoService;
import com.zzx.p2p.base.service.VideoAuthService;
import com.zzx.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 视频认证
 *
 * @author zzx
 * @date 2021-02-19 14:29:28
 */
@Controller
public class VideoAuthController {
    @Autowired
    private VideoAuthService videoAuthService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/videoAuth")
    public String videoAuth(@ModelAttribute("qo") VideoAuthQueryObject qo, Model model) {
        model.addAttribute("pageResult", videoAuthService.query(qo));
        return "videoAuth/list";
    }

    @RequestMapping("/videoAuth_audit")
    @ResponseBody
    public JSONResult videoAuthAudit(Long loginInfoValue, String remark, int state) {
        videoAuthService.audit(loginInfoValue, remark, state);
        return new JSONResult();
    }

    /**
     * 用于用户的autocomplete
     *
     * @param keyword
     * @return
     */
    @RequestMapping("/videoAuth_autocomplete")
    @ResponseBody
    public List<Map<String, Object>> autocomplete(String keyword) {
        return userInfoService.autocomplete(keyword);
    }
}
