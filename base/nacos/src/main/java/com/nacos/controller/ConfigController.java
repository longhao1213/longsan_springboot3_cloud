package com.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("config")
@RefreshScope
public class ConfigController {

    @Value(value = "${server.port}")
    private String useLocalCache;

    @GetMapping(value = "/get")
    @ResponseBody
    public String get() {
        return useLocalCache;
    }
}