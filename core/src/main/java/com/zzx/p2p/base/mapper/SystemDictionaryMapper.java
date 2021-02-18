package com.zzx.p2p.base.mapper;

import com.zzx.p2p.base.domain.SystemDictionary;
import com.zzx.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryMapper {
    int insert(SystemDictionary record);

    SystemDictionary selectByPrimaryKey(Long id);

    List<SystemDictionary> selectAll();

    /**
     * 分页查询数据字典的方法
     * @param qo
     * @return
     */
    int queryForCount(SystemDictionaryQueryObject qo);
    List<SystemDictionary> query(SystemDictionaryQueryObject qo);

    int updateByPrimaryKey(SystemDictionary record);
}