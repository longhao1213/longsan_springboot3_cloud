package com.common.pojo;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 通用分页查询类
 * 可供其他查询类继承
 */
@Data
@NoArgsConstructor
public class BasePageQuery {

    /**
     * 当前页码（默认第1页）
     */
    @Min(value = 1, message = "页码必须大于等于1")
    private Integer currentPage = 1;

    /**
     * 每页条数（默认10条）
     */
    @Min(value = 1, message = "每页条数必须大于等于1")
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String sortBy;

    /**
     * 排序方向（ASC升序/DESC降序，默认升序）
     */
    private String sortDirection = "ASC";

    /**
     * 获取偏移量，用于数据库分页
     */
    public int getOffset() {
        return (currentPage - 1) * pageSize;
    }
}