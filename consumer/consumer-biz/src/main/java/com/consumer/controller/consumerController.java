package com.consumer.controller;

import com.common.pojo.ApiResponse;
import com.producer.api.controller.ProducerInnerApi;
import com.producer.api.domain.ProducerVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class consumerController {

    private final ProducerInnerApi producerInnerApi;

    @GetMapping("/getConsumer")
    public ApiResponse<String> getConsumer(@RequestParam String consumerName) {
        log.info("调用getConsumer");
        ProducerVo producerVo = new ProducerVo();
        producerVo.setId(1L);
        producerVo.setName("producer");
        String producer = producerInnerApi.getProducer(producerVo);
        log.info("远程方法调用结束");
        System.out.println("TraceID: " + MDC.get("traceId"));
        System.out.println("SpanID: " + MDC.get("spanId"));
        return ApiResponse.success(producer + consumerName);
    }

}
