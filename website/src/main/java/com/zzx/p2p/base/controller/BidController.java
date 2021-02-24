package com.zzx.p2p.base.controller;

import com.zzx.p2p.base.annotation.RequireLogin;
import com.zzx.p2p.base.util.JSONResult;
import com.zzx.p2p.business.service.BidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 投标
 *
 * @author zzx
 * @date 2021-02-24 9:54:15
 */
@Controller
public class BidController {

    @Autowired
    private BidRequestService bidRequestService;

    @RequireLogin
    @RequestMapping("/borrow_bid")
    @ResponseBody
    public JSONResult bid(Long bidRequestId, BigDecimal amount) {
        bidRequestService.bid(bidRequestId, amount);
        return new JSONResult();
    }
}
