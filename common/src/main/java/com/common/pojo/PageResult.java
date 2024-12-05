package com.common.pojo;

import lombok.Data;

import java.util.List;

/**
 * 通用分页查询结果封装类
 *
 * @param <T> 数据类型
 */
@Data
public class PageResult<T> {

    /**
     * 当前页码
     */
    private Integer currentPage;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     * 数据列表
     */
    private List<T> records;

    public PageResult(Integer currentPage, Integer pageSize, Long total, List<T> records) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = total;
        this.records = records;
        this.totalPages = (int) Math.ceil((double) total / pageSize);
    }

    public static <T> PageResult<T> of(Integer currentPage, Integer pageSize, Long total, List<T> records) {
        return new PageResult<>(currentPage, pageSize, total, records);
    }
}