package com.zzx.p2p.base.query;

import com.zzx.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 实名认证查询对象
 *
 * @author zzx
 * @date 2021-02-18 16:06
 */
@Getter
@Setter
public class RealAuthQueryObject extends QueryObject {
    private int state = -1;
    private Date beginDate;
    private Date endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate == null ? null : DateUtil.endOfDay(endDate);
    }
}
