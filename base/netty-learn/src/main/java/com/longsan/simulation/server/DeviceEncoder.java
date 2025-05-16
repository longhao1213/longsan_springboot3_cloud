package com.longsan.simulation.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * 编码器
 */
public class DeviceEncoder extends MessageToByteEncoder<DeviceMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, DeviceMessage msg, ByteBuf out) {
        byte[] content = msg.getContent().getBytes(StandardCharsets.UTF_8);
        out.writeByte(msg.getType());
        out.writeInt(content.length);
        out.writeBytes(content);
    }
}