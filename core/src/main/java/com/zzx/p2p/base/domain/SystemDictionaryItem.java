package com.zzx.p2p.base.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典明细
 *
 * @author zzx
 * @date 2021-02-05 10:03:10
 */
@Getter
@Setter
public class SystemDictionaryItem extends BaseDomain {
    private Long parentId;
    private String title;
    private int sequence;

    /**
     * 返回当前的json字符串
     *
     * @return
     */
    public String getJsonString() {
        Map<String, Object> json = new HashMap<String, Object>(8);
        json.put("id", id);
        json.put("parentId", parentId);
        json.put("title", title);
        json.put("sequence", sequence);
        return JSONObject.toJSONString(json);
    }
}
