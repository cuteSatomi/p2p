package com.zzx.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

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
}
