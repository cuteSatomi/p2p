package com.zzx.p2p.business.domain;

import com.zzx.p2p.base.domain.BaseDomain;
import com.zzx.p2p.base.util.BidConst;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * 系统账户
 * <p>
 * 系统账户实际上是一条一条的统计信息，每一条信息都代表在这个会计期间之内的系统总账户余额和系统的利润
 * <p>
 * 比如，系统第一次初始化的时候，就会产生一条从初始化开始的会计期间，由系统管理员设置该会计期间的账户初始余额(比如100W)
 * 这就代表系统开始运行的时候，系统账户上面有100W，
 * <p>
 * 在整个系统运行期间，用户的充值操作，取现操作，系统垫付操作，抽取管理费用等，都会反映到该区间的系统账户；
 * 到了一年结束的时候，管理员可以对该时间段进行一个会计期间结算，代表比如一年完了，
 * <p>
 * 会计期间结束后，立刻会生成一个新的会计期间，这个新的会计期间的初始余额就是老会计期间的最后余额；
 * 并且在这里以后可能还会有一个操作，就是账户流水在本会计期间结束后，会自动的转接一张表，每一个会计期间都对应这个会计期间的账户流水表；
 * <p>
 * 在查询流水的时候，只能按照账户会计期间来查询（比如用户就最多选择1年的时间范围来查询流水）
 *
 * @author Stef
 */
@Setter
@Getter
public class SystemAccount extends BaseDomain {
    private static final long serialVersionUID = -644577214005040920L;

    /** 版本号 */
    private int version;
    /** 平台账户剩余金额 */
    private BigDecimal usableAmount = BidConst.ZERO;
    /** 平台账户冻结金额 */
    private BigDecimal frozenAmount = BidConst.ZERO;
}
