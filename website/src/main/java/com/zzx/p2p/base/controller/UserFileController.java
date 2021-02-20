package com.zzx.p2p.base.controller;

import com.zzx.p2p.base.domain.UserFile;
import com.zzx.p2p.base.service.SystemDictionaryService;
import com.zzx.p2p.base.service.UserFileService;
import com.zzx.p2p.base.util.JSONResult;
import com.zzx.p2p.base.util.UploadUtil;
import com.zzx.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;

/**
 * 风控资料相关
 *
 * @author zzx
 * @date 2021-02-19 21:29
 */
@Controller
public class UserFileController {
    @Autowired
    private UserFileService userFileService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private SystemDictionaryService systemDictionaryService;

    @RequestMapping("/userFile")
    public String userFile(Model model) {
        List<UserFile> userFiles = userFileService.listFilesByHasType(UserContext.getCurrent().getId(), false);
        // 有没有选择类型的风控资料
        if (userFiles != null && userFiles.size() > 0) {
            model.addAttribute("userFiles", userFiles);
            model.addAttribute("fileTypes", systemDictionaryService.listByParentSn("userFileType"));
            return "userFiles_commit";
        } else {
            userFiles = userFileService.listFilesByHasType(UserContext.getCurrent().getId(), true);
            model.addAttribute("userFiles", userFiles);
            return "userFiles";
        }
    }

    /**
     * 处理用户选择风控文件类型
     *
     * @return
     */
    @RequestMapping("/userFile_selectType")
    @ResponseBody
    public JSONResult userFileSelectType(Long[] fileType, Long[] id) {
        // 简单的校验
        if (fileType != null && id != null && fileType.length == id.length) {
            userFileService.batchUpdateFileType(fileType, id);
        }
        return new JSONResult();
    }

    /**
     * 处理上传用户风控文件
     *
     * @param files
     * @return
     */
    @RequestMapping("/userFileUpload")
    @ResponseBody
    public JSONResult userFileUpload(@RequestParam("file") MultipartFile[] files) {
        String basePath = servletContext.getRealPath("/upload");
        try {
            for (MultipartFile file : files) {
                String fileName = UploadUtil.upload(file, basePath);
                fileName = "/upload/" + fileName;
                userFileService.apply(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONResult(false, "文件上传失败");
        }
        return new JSONResult();
    }
}
