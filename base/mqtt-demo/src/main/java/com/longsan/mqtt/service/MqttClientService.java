package com.longsan.mqtt.service;

import com.longsan.mqtt.domain.MqttProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqttClientService {

    private final MqttProperties properties;
    private MqttClient client;

    public MqttClientService(MqttProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() throws InterruptedException {
        try {
            Thread.sleep(3000);
            client = new MqttClient(properties.getBroker(), properties.getClientId() + "-client");

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(properties.getUsername());
            options.setPassword(properties.getPassword().toCharArray());
            options.setCleanSession(true);

            client.connect(options);
            log.info("客户端连接成功");

        } catch (MqttException e) {
            log.error("客户端连接失败", e);
        }
    }

    public void publish(String topic, String msg) {
        try {
            MqttMessage message = new MqttMessage(msg.getBytes());
            message.setQos(1);
            client.publish(topic, message);
            log.info("客户端发送消息：topic={}, payload={}", topic, msg);
        } catch (MqttException e) {
            log.error("消息发布失败", e);
        }
    }
}