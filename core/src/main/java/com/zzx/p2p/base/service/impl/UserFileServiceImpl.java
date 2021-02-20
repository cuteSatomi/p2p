package com.zzx.p2p.base.service.impl;

import com.zzx.p2p.base.domain.SystemDictionaryItem;
import com.zzx.p2p.base.domain.UserFile;
import com.zzx.p2p.base.domain.UserInfo;
import com.zzx.p2p.base.mapper.UserFileMapper;
import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.base.query.UserFileQueryObject;
import com.zzx.p2p.base.service.UserFileService;
import com.zzx.p2p.base.service.UserInfoService;
import com.zzx.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zzx
 * @date 2021-02-19 21:28
 */
@Service
public class UserFileServiceImpl implements UserFileService {
    @Autowired
    private UserFileMapper userFileMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public void apply(String fileName) {
        UserFile userFile = new UserFile();
        userFile.setApplier(UserContext.getCurrent());
        userFile.setApplyTime(new Date());
        userFile.setImage(fileName);
        userFile.setState(UserFile.STATE_NORMAL);

        // 插入表中
        userFileMapper.insert(userFile);
    }

    @Override
    public List<UserFile> listFilesByHasType(Long id, boolean hasType) {
        return userFileMapper.listFilesByHasType(id, hasType);
    }

    @Override
    public void batchUpdateFileType(Long[] fileTypes, Long[] ids) {
        for (int i = 0; i < fileTypes.length; i++) {
            UserFile userFile = userFileMapper.selectByPrimaryKey(ids[i]);
            SystemDictionaryItem item = new SystemDictionaryItem();
            item.setId(fileTypes[i]);
            userFile.setFileType(item);
            userFileMapper.updateByPrimaryKey(userFile);
        }
    }

    @Override
    public PageResult query(UserFileQueryObject qo) {
        int count = userFileMapper.queryForCount(qo);
        if (count > 0) {
            List<UserFile> list = userFileMapper.query(qo);
            return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void audit(Long id, int score, String remark, int state) {
        // 查询出UserFile
        UserFile userFile = userFileMapper.selectByPrimaryKey(id);
        if (userFile != null && userFile.getState() == UserFile.STATE_NORMAL) {
            // 设置通用属性
            userFile.setAuditor(UserContext.getCurrent());
            userFile.setAuditTime(new Date());
            userFile.setState(state);
            if (state == UserFile.STATE_AUDIT) {
                userFile.setScore(score);
                UserInfo userInfo = userInfoService.get(userFile.getApplier().getId());
                userInfo.setScore(userInfo.getScore() + score);
                userInfoService.update(userInfo);
            }
            userFileMapper.updateByPrimaryKey(userFile);
        }
    }
}
