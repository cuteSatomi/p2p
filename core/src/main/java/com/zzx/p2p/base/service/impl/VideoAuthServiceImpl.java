package com.zzx.p2p.base.service.impl;

import com.zzx.p2p.base.domain.LoginInfo;
import com.zzx.p2p.base.domain.UserInfo;
import com.zzx.p2p.base.domain.VideoAuth;
import com.zzx.p2p.base.mapper.VideoAuthMapper;
import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.base.query.VideoAuthQueryObject;
import com.zzx.p2p.base.service.UserInfoService;
import com.zzx.p2p.base.service.VideoAuthService;
import com.zzx.p2p.base.util.BitStatesUtils;
import com.zzx.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zzx
 * @date 2021-02-19 14:26:22
 */
@Service
public class VideoAuthServiceImpl implements VideoAuthService {
    @Autowired
    private VideoAuthMapper videoAuthMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public PageResult query(VideoAuthQueryObject qo) {
        int count = videoAuthMapper.queryForCount(qo);
        if (count > 0) {
            List<VideoAuth> list = videoAuthMapper.query(qo);
            return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void audit(Long loginInfoValue, String remark, int state) {
        UserInfo user = userInfoService.get(loginInfoValue);
        if (user != null && !user.getIsVideoAuth()) {
            // 如果用户没有视频认证
            VideoAuth videoAuth = new VideoAuth();

            LoginInfo applier = new LoginInfo();
            applier.setId(loginInfoValue);
            videoAuth.setApplier(applier);
            videoAuth.setApplyTime(new Date());
            videoAuth.setAuditor(UserContext.getCurrent());
            videoAuth.setAuditTime(new Date());
            videoAuth.setRemark(remark);
            videoAuth.setState(state);
            // 插入数据
            videoAuthMapper.insert(videoAuth);

            if (state == VideoAuth.STATE_AUDIT) {
                // 如果审核通过，则修改用户状态码
                user.addState(BitStatesUtils.OP_VIDEO_AUTH);
                // 执行更新用户表
                userInfoService.update(user);
            }
        }
    }
}
