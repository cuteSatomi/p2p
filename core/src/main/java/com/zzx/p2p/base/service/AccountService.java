package com.zzx.p2p.base.service;

import com.zzx.p2p.base.domain.Account;

/**
 * 账户相关服务
 *
 * @author zzx
 * @date 2021-02-05 11:30:22
 */
public interface AccountService {
    /**
     * 写完mapper马上写service，因为这个update是支持乐观锁的
     * @param account
     */
    void update(Account account);
}
