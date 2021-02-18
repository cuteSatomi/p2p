package com.zzx.p2p.base.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典分类
 *
 * @author zzx
 * @date 2021-02-05 10:06:23
 */
@Getter
@Setter
public class SystemDictionary extends BaseDomain {
    private String sn;
    private String title;

    /**
     * 返回当前的json字符串
     *
     * @return
     */
    public String getJsonString() {
        Map<String, Object> json = new HashMap<String, Object>(8);
        json.put("id", id);
        json.put("sn", sn);
        json.put("title", title);
        return JSONObject.toJSONString(json);
    }
}
