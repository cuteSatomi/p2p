package com.zzx.p2p.business.service;

import com.zzx.p2p.base.query.PageResult;
import com.zzx.p2p.business.domain.BidRequest;
import com.zzx.p2p.business.domain.BidRequestAuditHistory;
import com.zzx.p2p.business.query.BidRequestQueryObject;

import java.math.BigDecimal;
import java.util.List;

/**
 * 借款相关
 *
 * @author zzx
 * @date 2021-02-20 17:33:24
 */
public interface BidRequestService {
    void update(BidRequest bidRequest);

    /**
     * 判断用户是否可以发起借款申请
     *
     * @param loginInfoId
     * @return
     */
    boolean canApplyBidRequest(Long loginInfoId);

    /**
     * 申请借款
     *
     * @param bidRequest
     */
    void apply(BidRequest bidRequest);

    PageResult query(BidRequestQueryObject qo);

    /**
     * 发标前审核
     *
     * @param id
     * @param state
     * @param remark
     */
    void publishAudit(Long id, int state, String remark);

    BidRequest get(Long id);

    /**
     * 根据一个标查询出该标的所有审核历史
     *
     * @param id
     * @return
     */
    List<BidRequestAuditHistory> listAuditHistoryByBidRequest(Long id);

    /**
     * 查询首页需要展示的数据
     *
     * @return
     */
    List<BidRequest> listIndex(int size);

    /**
     * 投标
     *
     * @param bidRequestId 对应标的id
     * @param amount       投标金额
     */
    void bid(Long bidRequestId, BigDecimal amount);

    /**
     * 满标一审
     *
     * @param id
     * @param state
     * @param remark
     */
    void fullAudit1(Long id, int state, String remark);

    /**
     * 满标二审
     * @param id
     * @param state
     * @param remark
     */
    void fullAudit2(Long id, int state, String remark);
}
