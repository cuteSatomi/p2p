package com.zzx.p2p.base.mapper;

import com.zzx.p2p.base.domain.VideoAuth;
import com.zzx.p2p.base.query.VideoAuthQueryObject;

import java.util.List;

public interface VideoAuthMapper {
    int insert(VideoAuth record);

    VideoAuth selectByPrimaryKey(Long id);

    /**
     * 分页查询相关
     * @param qo
     * @return
     */
    int queryForCount(VideoAuthQueryObject qo);
    List<VideoAuth> query(VideoAuthQueryObject qo);
}