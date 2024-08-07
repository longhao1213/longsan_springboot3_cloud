package com.security.controller;

import com.security.vo.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@Tag(name = "测试接口")
public class TestController {

    @GetMapping(value = "/hello")
    @Operation(summary = "测试hello")
    public ResponseResult<String> sayHello() {
        return ResponseResult.ok("hello");
    }
}
