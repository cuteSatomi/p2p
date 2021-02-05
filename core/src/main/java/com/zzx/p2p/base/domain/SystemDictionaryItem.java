package com.zzx.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

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
}
