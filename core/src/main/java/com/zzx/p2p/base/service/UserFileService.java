package com.zzx.p2p.base.service;

import com.zzx.p2p.base.domain.UserFile;
import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.base.query.UserFileQueryObject;

import java.util.List;

/**
 * 风控资料服务
 *
 * @author zzx
 * @date 2021-02-19 21:27
 */
public interface UserFileService {
    /**
     * 上传公共资料对象
     *
     * @param fileName
     */
    void apply(String fileName);

    /**
     * 根据是否选择了文件类型列出一个用户的资料对象
     *
     * @param id
     * @param hasType 如果为true表示有类型，反之没有选择类型
     * @return
     */
    List<UserFile> listFilesByHasType(Long id, boolean hasType);

    /**
     * 批量处理用户风控资料
     *
     * @param fileTypes
     * @param ids
     */
    void batchUpdateFileType(Long[] fileTypes, Long[] ids);

    PageResult query(UserFileQueryObject qo);

    List<UserFile> queryForList(UserFileQueryObject qo);

    /**
     * 风控材料审核
     *
     * @param id
     * @param score
     * @param remark
     * @param state
     */
    void audit(Long id, int score, String remark, int state);
}
