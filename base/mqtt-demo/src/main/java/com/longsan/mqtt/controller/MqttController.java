package com.longsan.mqtt.controller;

import com.longsan.mqtt.service.MqttClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mqtt")
@RequiredArgsConstructor
public class MqttController {

    private final MqttClientService mqttClientService;

    @PostMapping("/send")
    public String send(@RequestParam String topic, @RequestParam String msg) {
        mqttClientService.publish(topic, msg);
        return "发送成功";
    }
}