package com.zzx.p2p.base.controller;

import com.zzx.p2p.base.annotation.RequireLogin;
import com.zzx.p2p.base.domain.UserInfo;
import com.zzx.p2p.base.service.SystemDictionaryService;
import com.zzx.p2p.base.service.UserInfoService;
import com.zzx.p2p.base.util.JSONResult;
import com.zzx.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户个人资料
 *
 * @author zzx
 * @date 2021-02-14 20:52
 */
@Controller
public class BasicInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SystemDictionaryService systemDictionaryService;

    @RequireLogin
    @RequestMapping("/basicInfo")
    public String basicInfo(Model model) {
        // 添加当前用户
        model.addAttribute("userInfo", userInfoService.get(UserContext.getCurrent().getId()));
        model.addAttribute("educationBackgrounds", systemDictionaryService.listByParentSn("educationBackground"));
        model.addAttribute("incomeGrades", systemDictionaryService.listByParentSn("incomeGrade"));
        model.addAttribute("marriages", systemDictionaryService.listByParentSn("marriage"));
        model.addAttribute("kidCounts", systemDictionaryService.listByParentSn("kidCount"));
        model.addAttribute("houseConditions", systemDictionaryService.listByParentSn("houseCondition"));
        return "userInfo";
    }

    @RequireLogin
    @RequestMapping("/basicInfo_save")
    @ResponseBody
    public JSONResult basicInfoSave(UserInfo userInfo){
        userInfoService.updateBasicInfo(userInfo);
        return new JSONResult();
    }
}
