package com.zzx.p2p.business.service.impl;

import com.zzx.p2p.base.util.BidConst;
import com.zzx.p2p.business.domain.BidRequest;
import com.zzx.p2p.business.domain.SystemAccount;
import com.zzx.p2p.business.domain.SystemAccountFlow;
import com.zzx.p2p.business.mapper.SystemAccountFlowMapper;
import com.zzx.p2p.business.mapper.SystemAccountMapper;
import com.zzx.p2p.business.service.SystemAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zzx
 * @date 2021-02-25 14:15:01
 */
@Service
public class SystemAccountServiceImpl implements SystemAccountService {

    @Autowired
    private SystemAccountMapper systemAccountMapper;

    @Autowired
    private SystemAccountFlowMapper systemAccountFlowMapper;

    @Override
    public void initAccount() {
        SystemAccount current = this.systemAccountMapper.selectCurrent();
        if (current == null) {
            current = new SystemAccount();
            this.systemAccountMapper.insert(current);
        }
    }

    @Override
    public void update(SystemAccount systemAccount) {
        int ret = systemAccountMapper.updateByPrimaryKey(systemAccount);
        if (ret == 0) {
            throw new RuntimeException("乐观锁失败，Account: " + systemAccount.getId());
        }
    }

    @Override
    public void chargeBorrowFee(BidRequest br, BigDecimal manageChargeFee) {
        // 得到当前系统账户
        SystemAccount current = systemAccountMapper.selectCurrent();
        // 修改账户余额
        current.setUsableAmount(current.getUsableAmount().add(manageChargeFee));
        // 生成系统账户收款流水
        SystemAccountFlow flow = new SystemAccountFlow();
        flow.setAccountActionType(BidConst.SYSTEM_ACCOUNT_ACTION_TYPE_MANAGE_CHARGE);
        flow.setAmount(manageChargeFee);
        flow.setBalance(current.getUsableAmount());
        flow.setCreatedDate(new Date());
        flow.setFrozenAmount(current.getFrozenAmount());
        flow.setNote("借款" + br.getTitle() + "成功,收取借款手续费:" + manageChargeFee);
        flow.setSystemAccountId(current.getId());
        this.systemAccountFlowMapper.insert(flow);
        this.update(current);
    }
}
