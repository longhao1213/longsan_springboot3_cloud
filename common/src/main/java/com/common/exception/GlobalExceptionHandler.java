package com.common.exception;

import com.common.pojo.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理方法参数校验失败的异常 (针对 @Valid/@Validated)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createResponse(message));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResponse<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return createResponse("字段："+ex.getParameterName() + "不能为空");
    }

    /**
     * 处理普通请求参数校验失败的异常 (针对单个字段)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createResponse(message));
    }

    /**
     * 通用 BindException 处理器 (GET 请求参数校验失败)
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<String>> handleBindExceptions(BindException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createResponse(message));
    }

    /**
     * 创建统一的返回格式
     */
    private ApiResponse<String> createResponse(String message) {
        return ApiResponse.failure(message);
    }
}