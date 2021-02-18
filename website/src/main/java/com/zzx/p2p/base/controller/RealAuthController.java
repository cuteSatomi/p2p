package com.zzx.p2p.base.controller;

import com.zzx.p2p.base.annotation.RequireLogin;
import com.zzx.p2p.base.domain.RealAuth;
import com.zzx.p2p.base.domain.UserInfo;
import com.zzx.p2p.base.service.RealAuthService;
import com.zzx.p2p.base.service.UserInfoService;
import com.zzx.p2p.base.util.JSONResult;
import com.zzx.p2p.base.util.UploadUtil;
import com.zzx.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证相关控制类
 *
 * @author zzx
 * @date 2021-02-17 21:55
 */
@Controller
public class RealAuthController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RealAuthService realAuthService;

    @Autowired
    private ServletContext servletContext;

    @RequireLogin
    @RequestMapping("/realAuth")
    public String realAuth(Model model) {
        // 得到当前userInfo
        UserInfo current = userInfoService.get(UserContext.getCurrent().getId());

        if (current.getIsRealAuth()) {
            // 如果已经实名认证
            model.addAttribute("auditing", false);
            model.addAttribute("realAuth", realAuthService.get(current.getRealAuthId()));
            return "realAuth_result";
        } else {
            if (current.getRealAuthId() != null) {
                // 如果realAuthId不为null，则说明在审核中
                model.addAttribute("auditing", true);
                return "realAuth_result";
            } else {
                // 没有实名认证又没有正在审核，则跳转到实名认证的页面
                return "realAuth";
            }
        }
    }

    @RequestMapping("/realAuthUpload")
    @ResponseBody
    public Map<String, String> uploadPhoto(MultipartFile photo) {
        Map<String, String> map = new HashMap<String, String>();
        String basePath = servletContext.getRealPath("/upload");
        String fileName = "";
        try {
            fileName = UploadUtil.upload(photo, basePath);
            map.put("type", "success");
            map.put("msg", "上传图片成功！");
            map.put("filepath", servletContext.getContextPath() + "/upload");
            map.put("filename", fileName);
        } catch (IOException e) {
            map.put("type", "error");
            map.put("msg", "保存文件异常！");
            e.printStackTrace();
        }
        return map;
    }

    @RequireLogin
    @RequestMapping("/realAuth_save")
    @ResponseBody
    public JSONResult realAuthSave(RealAuth realAuth){
        realAuthService.apply(realAuth);
        return new JSONResult();
    }
}
