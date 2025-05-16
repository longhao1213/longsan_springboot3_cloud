package com.longsan.simulation.server;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 定义协议消息类
 */
@Data
@AllArgsConstructor
public class DeviceMessage {
    private byte type;      // 消息类型 0-注册，1-心跳 2-数据
    private String content; // 内容
}