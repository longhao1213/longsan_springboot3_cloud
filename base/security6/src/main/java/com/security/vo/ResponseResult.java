package com.security.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 统一响应类
 * @author ss_419
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseResult<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String msg;
    /**
     * 查询到的结果数据，
     */
    private T data;

    public ResponseResult(Integer code, String msg,T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseResult ok(String message) {
        return new ResponseResult(200, message, null);
    }
}
