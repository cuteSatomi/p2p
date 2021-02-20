package com.zzx.p2p.business.service.impl;

import com.zzx.p2p.business.domain.BidRequest;
import com.zzx.p2p.business.mapper.BidRequestMapper;
import com.zzx.p2p.business.service.BidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zzx
 * @date 2021-02-20 17:34:02
 */
@Service
public class BidRequestServiceImpl implements BidRequestService {
    @Autowired
    private BidRequestMapper bidRequestMapper;

    @Override
    public void update(BidRequest bidRequest) {
        int ret = bidRequestMapper.updateByPrimaryKey(bidRequest);
        if (ret == 0) {
            throw new RuntimeException("乐观锁失败  bidRequest:" + bidRequest.getId());
        }
    }
}
