package com.consumer.controller;

import com.common.pojo.ApiResponse;
import com.producer.api.controller.ProducerInnerApi;
import com.producer.api.domain.ProducerVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class consumerController {

    private final ProducerInnerApi producerInnerApi;

    @GetMapping("/getConsumer")
    public ApiResponse<String> getConsumer(@RequestParam String consumerName) {
        ProducerVo producerVo = new ProducerVo();
        producerVo.setId(1L);
        producerVo.setName("producer");
        String producer = producerInnerApi.getProducer(producerVo);
        return ApiResponse.success(producer + consumerName);
    }
}
