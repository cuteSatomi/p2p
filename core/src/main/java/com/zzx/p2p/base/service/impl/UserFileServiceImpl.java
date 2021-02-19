package com.zzx.p2p.base.service.impl;

import com.zzx.p2p.base.domain.SystemDictionaryItem;
import com.zzx.p2p.base.domain.UserFile;
import com.zzx.p2p.base.mapper.UserFileMapper;
import com.zzx.p2p.base.service.UserFileService;
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
        return userFileMapper.listFilesByHasType(id,hasType);
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
}
