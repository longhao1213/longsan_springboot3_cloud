package com.producer.controller.inner;

import com.producer.api.controller.ProducerInnerApi;
import com.producer.api.domain.ProducerVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/producer")
@Slf4j
public class ProducerInnerController implements ProducerInnerApi {
    @Override
    public String getProducer(ProducerVo producerVo) {
        log.info("开始调用远程方法getProducer");
        return producerVo.toString();
    }
}
