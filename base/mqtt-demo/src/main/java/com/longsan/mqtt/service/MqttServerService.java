package com.longsan.mqtt.service;

import com.longsan.mqtt.domain.MqttProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqttServerService {

    private final MqttProperties properties;
    private MqttClient client;

    public MqttServerService(MqttProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        try {
            client = new MqttClient(properties.getBroker(), properties.getClientId() + "-server");

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(properties.getUsername());
            options.setPassword(properties.getPassword().toCharArray());
            options.setCleanSession(true);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    log.warn("服务端连接丢失", cause);
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    log.info("服务端收到消息：topic={}, payload={}", topic, new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {}
            });

            client.connect(options);
            client.subscribe(properties.getTopic());
            log.info("服务端订阅完成：{}", properties.getTopic());

        } catch (MqttException e) {
            log.error("服务端连接失败", e);
        }
    }
}