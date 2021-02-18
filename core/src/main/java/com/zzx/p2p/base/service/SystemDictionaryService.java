package com.zzx.p2p.base.service;

import com.zzx.p2p.base.domain.SystemDictionary;
import com.zzx.p2p.base.domain.SystemDictionaryItem;
import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

/**
 * 数据字典相关服务
 *
 * @author zzx
 * @date 2021-02-14 15:45
 */
public interface SystemDictionaryService {
    /**
     * 查询数据字典
     * @param qo
     * @return
     */
    PageResult queryDictionaries(SystemDictionaryQueryObject qo);

    /**
     * 修改或者保存数据字典
     * @param dictionary
     */
    void saveOrUpdateDictionary(SystemDictionary dictionary);

    /**
     * 数据字典明细的查询
     * @param qo
     * @return
     */
    PageResult queryDictionaryItems(SystemDictionaryQueryObject qo);

    /**
     * 查询数据字典
     * @return
     */
    List<SystemDictionary> listAllDictionaries();

    /**
     * 保存数据字典明细
     * @param dictionaryItem
     */
    void saveOrUpdateDictionaryItem(SystemDictionaryItem dictionaryItem);

    /**
     * 根据sn查询出数据字典明细
     * @param sn
     * @return
     */
    List<SystemDictionaryItem> listByParentSn(String sn);
}
