package com.zzx.p2p.base.service.impl;

import com.zzx.p2p.base.domain.RealAuth;
import com.zzx.p2p.base.domain.UserInfo;
import com.zzx.p2p.base.mapper.RealAuthMapper;
import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.base.query.RealAuthQueryObject;
import com.zzx.p2p.base.service.RealAuthService;
import com.zzx.p2p.base.service.UserInfoService;
import com.zzx.p2p.base.util.BitStatesUtils;
import com.zzx.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zzx
 * @date 2021-02-17 22:06
 */
@Service
public class RealAuthServiceImpl implements RealAuthService {

    @Autowired
    private RealAuthMapper realAuthMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public RealAuth get(Long id) {
        return realAuthMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult query(RealAuthQueryObject qo) {
        int count = realAuthMapper.queryForCount(qo);
        if (count > 0) {
            List<RealAuth> list = realAuthMapper.query(qo);
            return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void apply(RealAuth realAuth) {
        // 得到当前登陆用户对象
        UserInfo current = userInfoService.get(UserContext.getCurrent().getId());
        // 判断当前用户没有实名认证，并且不在待审核状态
        if (!current.getIsRealAuth() && current.getRealAuthId() == null) {
            // 将实名认证对象保存
            realAuth.setApplyTime(new Date());
            realAuth.setState(RealAuth.STATE_NORMAL);
            realAuth.setApplier(UserContext.getCurrent());
            realAuthMapper.insert(realAuth);
            // 把实名认证的id设置给userInfo
            current.setRealAuthId(realAuth.getId());
            userInfoService.update(current);
        }
    }


    @Override
    public void audit(Long id, String remark, int state) {
        // 根据id得到realAuth对象
        RealAuth realAuth = get(id);
        // 如果对象存在且处于审核状态
        if (realAuth != null && realAuth.getState() == RealAuth.STATE_NORMAL) {
            // 设置通用属性
            realAuth.setAuditor(UserContext.getCurrent());
            realAuth.setAuditTime(new Date());
            realAuth.setState(state);

            // 得到申请人
            UserInfo applier = userInfoService.get(realAuth.getApplier().getId());
            // 如果是审核通过
            if (state == RealAuth.STATE_AUDIT) {
                if (!applier.getIsRealAuth()) {
                    applier.addState(BitStatesUtils.OP_REAL_AUTH);
                    applier.setRealName(realAuth.getRealName());
                    applier.setIdNumber(realAuth.getIdNumber());
                    applier.setRealAuthId(realAuth.getId());
                }
            } else {
                // 如果是审核拒绝，userInfo中的realAuthId设为空
                applier.setRealAuthId(null);
            }
            // 执行更新
            userInfoService.update(applier);
            realAuthMapper.updateByPrimaryKey(realAuth);
        }
    }
}
