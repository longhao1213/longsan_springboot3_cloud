package com.producer.controller.inner;

import com.producer.api.controller.ProducerInnerApi;
import com.producer.api.domain.ProducerVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/producer")
public class ProducerInnerController implements ProducerInnerApi {
    @Override
    public String getProducer(ProducerVo producerVo) {
        return producerVo.toString();
    }
}
