package com.zzx.p2p.mgrsite.base.controller;

import com.zzx.p2p.base.domain.SystemDictionary;
import com.zzx.p2p.base.domain.SystemDictionaryItem;
import com.zzx.p2p.base.query.SystemDictionaryQueryObject;
import com.zzx.p2p.base.service.SystemDictionaryService;
import com.zzx.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zzx
 * @date 2021-02-14 15:51
 */
@Controller
public class SystemDictionaryController {

    @Autowired
    private SystemDictionaryService systemDictionaryService;

    @RequestMapping("systemDictionary_list")
    public String systemDictionaryList(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model) {
        model.addAttribute("pageResult", systemDictionaryService.queryDictionaries(qo));
        return "systemdic/systemDictionary_list";
    }

    @RequestMapping("/systemDictionary_update")
    @ResponseBody
    public JSONResult systemDictionaryUpdate(SystemDictionary dictionary) {
        systemDictionaryService.saveOrUpdateDictionary(dictionary);
        return new JSONResult();
    }

    @RequestMapping("/systemDictionaryItem_list")
    public String systemDictionaryItemList(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model) {
        model.addAttribute("pageResult", systemDictionaryService.queryDictionaryItems(qo));
        model.addAttribute("systemDictionaryGroups",systemDictionaryService.listAllDictionaries());
        return "systemdic/systemDictionaryItem_list";
    }

    @RequestMapping("/systemDictionaryItem_update")
    @ResponseBody
    public JSONResult systemDictionaryItemUpdate(SystemDictionaryItem dictionaryItem){
        systemDictionaryService.saveOrUpdateDictionaryItem(dictionaryItem);
        return new JSONResult();
    }
}
