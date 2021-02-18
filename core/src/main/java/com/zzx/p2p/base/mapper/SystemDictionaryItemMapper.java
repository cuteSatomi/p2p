package com.zzx.p2p.base.mapper;

import com.zzx.p2p.base.domain.SystemDictionaryItem;
import com.zzx.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryItemMapper {

    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    int updateByPrimaryKey(SystemDictionaryItem record);

    /**
     * 分页查询相关
     * @param qo
     * @return
     */
    int queryForCount(SystemDictionaryQueryObject qo);
    List<SystemDictionaryItem> query(SystemDictionaryQueryObject qo);

    List<SystemDictionaryItem> listByParentSn(String sn);
}