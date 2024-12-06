package com.producer.api.controller;

import com.producer.api.domain.ProducerVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "producer",path = "/producer")
public interface ProducerInnerApi {

    @PostMapping("/getProducer")
    String getProducer(@RequestBody ProducerVo producerVo);
}
