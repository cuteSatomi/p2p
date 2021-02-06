package com.zzx.p2p.base.service.impl;

import com.zzx.p2p.base.domain.Account;
import com.zzx.p2p.base.mapper.AccountMapper;
import com.zzx.p2p.base.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zzx
 * @date 2021-02-05 11:30:31
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void update(Account account) {
        int ret = accountMapper.updateByPrimaryKey(account);
        if (ret == 0) {
            throw new RuntimeException("乐观锁失败，Account: " + account.getId());
        }
    }

    @Override
    public void add(Account account) {
        accountMapper.insert(account);
    }

    @Override
    public Account get(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }
}
