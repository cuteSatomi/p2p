package com.zzx.p2p.base.controller;

import com.zzx.p2p.base.util.BidConst;
import com.zzx.p2p.business.query.BidRequestQueryObject;
import com.zzx.p2p.business.service.BidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zzx
 * @date 2021-02-22 19:16
 */
@Controller
public class IndexController {
    @Autowired
    private BidRequestService bidRequestService;

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("bidRequests", bidRequestService.listIndex(5));
        return "main";
    }

    /**
     * 投资列表明细
     *
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("/invest_list")
    public String investList(BidRequestQueryObject qo, Model model) {
        if (qo.getBidRequestState() == -1) {
            qo.setBidRequestStates(new int[]{BidConst.BID_REQUEST_STATE_BIDDING,
                    BidConst.BID_REQUEST_STATE_PAYING_BACK,
                    BidConst.BID_REQUEST_STATE_COMPLETE_PAY_BACK});
        }
        model.addAttribute("pageResult", bidRequestService.query(qo));
        return "invest_list";
    }
}
