package com.zzx.p2p.base.controller;

import com.zzx.p2p.base.domain.LoginInfo;
import com.zzx.p2p.base.domain.UserInfo;
import com.zzx.p2p.base.query.UserFileQueryObject;
import com.zzx.p2p.base.service.AccountService;
import com.zzx.p2p.base.service.RealAuthService;
import com.zzx.p2p.base.service.UserFileService;
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

    @Autowired
    private RealAuthService realAuthService;

    @Autowired
    private UserFileService userFileService;

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
            model.addAttribute("minBidRequestAmount", BidConst.SMALLEST_BID_REQUEST_AMOUNT);
            // 最小投标金额
            model.addAttribute("minBidAmount", BidConst.SMALLEST_BID_AMOUNT);
            model.addAttribute("account", accountService.get(id));
            return "borrow_apply";
        } else {
            return "borrow_apply_result";
        }
    }

    @RequestMapping("/borrow_apply")
    public String borrowApply(BidRequest bidRequest) {
        bidRequestService.apply(bidRequest);
        return "redirect:/borrowInfo.do";
    }

    @RequestMapping("/borrow_info")
    public String borrowInfoDetail(Long id, Model model) {
        // bidRequest
        BidRequest bidRequest = bidRequestService.get(id);
        if (bidRequest != null) {
            // userInfo
            UserInfo applier = userInfoService.get(bidRequest.getCreateUser().getId());
            // realAuth
            model.addAttribute("realAuth", realAuthService.get(applier.getRealAuthId()));
            // userFiles
            UserFileQueryObject qo = new UserFileQueryObject();
            qo.setApplierId(applier.getId());
            qo.setPageSize(-1);
            qo.setCurrentPage(1);
            model.addAttribute("userFiles", userFileService.queryForList(qo));

            model.addAttribute("bidRequest", bidRequest);
            model.addAttribute("userInfo", applier);

            if (UserContext.getCurrent() != null) {
                // self：当前用户是否是借款人自己
                if(UserContext.getCurrent().getId().equals(applier.getId())){
                    model.addAttribute("self",true);
                }else {
                    model.addAttribute("self", false);
                    // account
                    model.addAttribute("account",accountService.get(UserContext.getCurrent().getId()));
                }

            } else {
                model.addAttribute("self", false);
            }
        }
        return "borrow_info";
    }
}
