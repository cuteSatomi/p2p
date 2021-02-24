package com.zzx.p2p.mgrsite.base.controller;

import com.zzx.p2p.base.domain.UserFile;
import com.zzx.p2p.base.domain.UserInfo;
import com.zzx.p2p.base.query.UserFileQueryObject;
import com.zzx.p2p.base.service.RealAuthService;
import com.zzx.p2p.base.service.UserFileService;
import com.zzx.p2p.base.service.UserInfoService;
import com.zzx.p2p.base.util.BidConst;
import com.zzx.p2p.base.util.JSONResult;
import com.zzx.p2p.business.domain.BidRequest;
import com.zzx.p2p.business.query.BidRequestQueryObject;
import com.zzx.p2p.business.service.BidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zzx
 * @date 2021-02-21 15:35
 */
@Controller
public class BidRequestController {
    @Autowired
    private BidRequestService bidRequestService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RealAuthService realAuthService;

    @Autowired
    private UserFileService userFileService;

    @RequestMapping("/bidRequest_publishAudit_list")
    public String bidRequestPublishAuditList(@ModelAttribute("qo") BidRequestQueryObject qo, Model model) {
        // 这个页面只能查询待发布状态
        qo.setBidRequestState(BidConst.BID_REQUEST_STATE_PUBLISH_PENDING);
        model.addAttribute("pageResult", bidRequestService.query(qo));
        return "bidrequest/publish_audit";
    }

    /**
     * 发标前审核
     *
     * @param id
     * @param state
     * @param remark
     * @return
     */
    @RequestMapping("/bidRequest_publishAudit")
    @ResponseBody
    public JSONResult bidRequestPublishAudit(Long id, int state, String remark) {
        bidRequestService.publishAudit(id, state, remark);
        return new JSONResult();
    }

    /**
     * 查询满标1审的列表
     *
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("/bidRequest_audit1_list")
    public String bidRequestFullAudit1List(@ModelAttribute("qo") BidRequestQueryObject qo, Model model) {
        // 这个页面只能查询待发布状态
        qo.setBidRequestState(BidConst.BID_REQUEST_STATE_APPROVE_PENDING_1);
        model.addAttribute("pageResult", bidRequestService.query(qo));
        return "bidrequest/audit1";
    }

    /**
     * 满标一审审核
     *
     * @param id
     * @param state
     * @param remark
     * @return
     */
    @RequestMapping("/bidRequest_audit1")
    @ResponseBody
    public JSONResult bidRequestAudit1(Long id, int state, String remark) {
        bidRequestService.fullAudit1(id, state, remark);
        return new JSONResult();
    }

    @RequestMapping("/borrow_info")
    public String borrowInfoDetail(Long id, Model model) {
        BidRequest bidRequest = bidRequestService.get(id);
        UserInfo userInfo = userInfoService.get(bidRequest.getCreateUser().getId());
        model.addAttribute("bidRequest", bidRequest);
        model.addAttribute("userInfo", userInfo);
        // 这个标的历史审核信息
        model.addAttribute("audits", bidRequestService.listAuditHistoryByBidRequest(id));
        // 该借款人的实名认证信息
        model.addAttribute("realAuth", realAuthService.get(userInfo.getRealAuthId()));
        // 该借款人的风控资料信息
        UserFileQueryObject qo = new UserFileQueryObject();
        qo.setApplierId(userInfo.getId());
        qo.setState(UserFile.STATE_AUDIT);
        qo.setPageSize(-1);
        model.addAttribute("userFiles", userFileService.queryForList(qo));
        return "bidrequest/borrow_info";
    }
}
