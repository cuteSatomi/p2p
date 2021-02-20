package com.zzx.p2p.base.mapper;

import com.zzx.p2p.base.domain.UserFile;
import com.zzx.p2p.base.query.UserFileQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFileMapper {
    int insert(UserFile record);

    UserFile selectByPrimaryKey(Long id);

    List<UserFile> selectAll();

    int updateByPrimaryKey(UserFile record);

    /**
     * 根据是否选择了文件类型列出一个用户的资料对象
     *
     * @param id
     * @param hasType 如果为true表示有类型，反之没有选择类型
     * @return
     */
    List<UserFile> listFilesByHasType(@Param("id") Long id, @Param("hasType") boolean hasType);

    /**
     * 分页查询相关
     * @param qo
     * @return
     */
    int queryForCount(UserFileQueryObject qo);
    List<UserFile> query(UserFileQueryObject qo);
}