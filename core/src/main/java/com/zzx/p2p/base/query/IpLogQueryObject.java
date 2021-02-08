package com.zzx.p2p.base.query;

import com.zzx.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 登录日志查询对象
 *
 * @author zzx
 * @date 2021-02-07 11:44:34
 */
@Getter
@Setter
public class IpLogQueryObject extends QueryObject {
    private Date beginDate;
    private Date endDate;
    private int state = -1;
    private String username;
    private int userType = -1;

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

    public String getUsername() {
        return StringUtils.hasLength(username) ? username : null;
    }
}
