package com.zzx.p2p.base.query;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzx
 * @date 2021-02-07 11:28:51
 */
@Getter
@SuppressWarnings("all")
public class PageResult {
    /**
     * 当前页的结果集查询
     */
    private List listData;
    /**
     * 总数据条数
     */
    private Integer totalCount;

    private Integer currentPage = 1;
    private Integer pageSize = 10;

    /**
     * 上一页
     */
    private Integer prevPage;
    /**
     * 下一页
     */
    private Integer nextPage;
    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 总数据为空则返回一个空集
     *
     * @param pageSize
     * @return
     */
    public static PageResult empty(Integer pageSize) {
        return new PageResult(new ArrayList<>(), 0, 1, pageSize);
    }

    public int getTotalPage() {
        return totalPage == 0 ? 1 : totalPage;
    }

    public PageResult(List listData, Integer totalCount, Integer currentPage, Integer pageSize) {
        this.listData = listData;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;

        this.totalPage = this.totalCount % this.pageSize == 0 ?
                this.totalCount / this.pageSize :
                this.totalCount / this.pageSize + 1;
        this.prevPage = this.currentPage - 1 >= 1 ? this.currentPage - 1 : 1;
        this.nextPage = this.currentPage + 1 <= this.totalPage ? this.currentPage + 1 : this.totalPage;
    }
}
