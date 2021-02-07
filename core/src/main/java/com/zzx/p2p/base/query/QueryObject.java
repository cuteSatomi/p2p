package com.zzx.p2p.base.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 所有高级查询对象的共同代码
 *
 * @author zzx
 * @date 2021-02-07 11:38:18
 */
@Getter
@Setter
abstract public class QueryObject {
    private Integer currentPage = 1;
    private Integer pageSize = 5;

    public int getStart() {
        return (currentPage - 1) * pageSize;
    }
}
