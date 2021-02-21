package com.zzx.p2p.base.controller;

import com.zzx.p2p.base.domain.LoginInfo;
import com.zzx.p2p.base.service.AccountService;
import com.zzx.p2p.base.service.UserInfoService;
import com.zzx.p2p.base.util.BidConst;
import com.zzx.p2p.base.util.UserContext;
import com.zzx.p2p.business.domain.BidRequest;
import com.zzx.p2p.business.service.BidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zzx
 * @date 2021-02-14 15:06
 */
@Controller
public class BorrowController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BidRequestService bidRequestService;

    /**
     * 到我要借款页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/borrow")
    public String borrowIndex(Model model) {
        LoginInfo current = UserContext.getCurrent();
        if (current == null) {
            // 如果未登陆则重定向到一个静态页面
            return "redirect:borrow.html";
        } else {
            model.addAttribute("account", accountService.get(current.getId()));
            model.addAttribute("userInfo", userInfoService.get(current.getId()));
            model.addAttribute("creditBorrowScore", BidConst.BASE_BORROW_SCORE);
            return "borrow";
        }
    }

    /**
     * 导向到借款申请页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/borrowInfo")
    public String borrowInfo(Model model) {
        Long id = UserContext.getCurrent().getId();
        if (bidRequestService.canApplyBidRequest(id)) {
            // 能够申请借款
            // 最小借款金额
            model.addAttribute("minBidRequestAmount",BidConst.SMALLEST_BID_REQUEST_AMOUNT);
            // 最小投标金额
            model.addAttribute("minBidAmount",BidConst.SMALLEST_BID_AMOUNT);
            model.addAttribute("account",accountService.get(id));
            return "borrow_apply";
        } else {
            return "borrow_apply_result";
        }
    }

    @RequestMapping("/borrow_apply")
    public String borrowApply(BidRequest bidRequest){
        bidRequestService.apply(bidRequest);
        return "redirect:/borrowInfo.do";
    }
}
