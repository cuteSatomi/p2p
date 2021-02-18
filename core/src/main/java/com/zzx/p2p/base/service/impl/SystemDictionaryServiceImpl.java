package com.zzx.p2p.base.service.impl;

import com.zzx.p2p.base.domain.SystemDictionary;
import com.zzx.p2p.base.domain.SystemDictionaryItem;
import com.zzx.p2p.base.mapper.SystemDictionaryItemMapper;
import com.zzx.p2p.base.mapper.SystemDictionaryMapper;
import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.base.query.SystemDictionaryQueryObject;
import com.zzx.p2p.base.service.SystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zzx
 * @date 2021-02-14 15:46
 */
@Service
public class SystemDictionaryServiceImpl implements SystemDictionaryService {

    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;

    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;

    @Override
    public PageResult queryDictionaries(SystemDictionaryQueryObject qo) {
        int count = systemDictionaryMapper.queryForCount(qo);
        if (count > 0) {
            List<SystemDictionary> list = systemDictionaryMapper.query(qo);
            return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public List<SystemDictionary> listAllDictionaries() {
        return systemDictionaryMapper.selectAll();
    }

    @Override
    public void saveOrUpdateDictionary(SystemDictionary dictionary) {
        if (dictionary.getId() != null) {
            systemDictionaryMapper.updateByPrimaryKey(dictionary);
        } else {
            systemDictionaryMapper.insert(dictionary);
        }
    }

    @Override
    public PageResult queryDictionaryItems(SystemDictionaryQueryObject qo) {
        int count = systemDictionaryItemMapper.queryForCount(qo);
        if (count > 0) {
            List<SystemDictionaryItem> list = systemDictionaryItemMapper.query(qo);
            return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void saveOrUpdateDictionaryItem(SystemDictionaryItem dictionaryItem) {
        if (dictionaryItem.getId() != null) {
            systemDictionaryItemMapper.updateByPrimaryKey(dictionaryItem);
        } else {
            systemDictionaryItemMapper.insert(dictionaryItem);
        }
    }

    @Override
    public List<SystemDictionaryItem> listByParentSn(String sn) {
        return systemDictionaryItemMapper.listByParentSn(sn);
    }
}
