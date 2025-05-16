package com.longsan.simulation.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 业务处理器
 */
@Slf4j
public class DeviceServerHandler extends SimpleChannelInboundHandler<DeviceMessage> {

    // 绑定设备ID与Channel
    private static final Map<String, Channel> DEVICE_CHANNEL_MAP = new ConcurrentHashMap<>();


    @Override
    public void channelRead0(ChannelHandlerContext ctx, DeviceMessage msg) {
        switch (msg.getType()) {
            case 0 -> {
                String deviceId = msg.getContent();
                ctx.channel().attr(AttributeKeys.DEVICE_ID).set(deviceId);
                DEVICE_CHANNEL_MAP.put(deviceId, ctx.channel());
                log.info("设备注册成功：{}", deviceId);
                // TODO: 插入或更新设备状态到数据库
            }
            case 1 -> {
                String deviceId = ctx.channel().attr(AttributeKeys.DEVICE_ID).get();
                log.info("收到心跳：" + deviceId);
                // TODO: 更新设备最后心跳时间到数据库
            }
            case 2 -> {
                String deviceId = ctx.channel().attr(AttributeKeys.DEVICE_ID).get();
                log.info("设备[" + deviceId + "]上传数据：" + msg.getContent());
                // TODO: 将数据保存到数据库（例如 MySQL）
            }
            default -> log.error("未知类型消息");
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent idleEvent) {
            if (idleEvent.state() == IdleState.READER_IDLE) {
                String deviceId = ctx.channel().attr(AttributeKeys.DEVICE_ID).get();
                System.out.println("设备[" + deviceId + "]心跳超时，断开连接");
                DEVICE_CHANNEL_MAP.remove(deviceId);
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String deviceId = ctx.channel().attr(AttributeKeys.DEVICE_ID).get();
        if (deviceId != null) {
            DEVICE_CHANNEL_MAP.remove(deviceId);
            System.out.println("设备断开连接：" + deviceId);
            // TODO: 标记设备为离线状态
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("异常关闭：" + cause.getMessage());
        ctx.close();
    }
}