package com.zzx.p2p.base.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 风控材料对象
 *
 * @author zzx
 * @date 2021-02-19 20:57
 */
@Getter
@Setter
public class UserFile extends BaseAuditDomain {
    /** 风控材料图片 */
    private String image;
    /** 风控材料分类 */
    private SystemDictionaryItem fileType;
    /** 风控材料评分 */
    private int score;

    /**
     * 返回当前的json字符串
     *
     * @return
     */
    public String getJsonString() {
        Map<String, Object> json = new HashMap<String, Object>(8);
        json.put("id", id);
        json.put("applier", applier.getUsername());
        json.put("fileType", fileType.getTitle());
        json.put("image", image);
        return JSONObject.toJSONString(json);
    }
}
