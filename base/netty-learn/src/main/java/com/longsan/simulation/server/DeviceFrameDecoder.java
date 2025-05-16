package com.longsan.simulation.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 自定义 ByteBuf 解码器，将帧数据解析为 DeviceMessage
 */
public class DeviceFrameDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        byte type = in.readByte();
        int length = in.readInt();
        byte[] contentBytes = new byte[length];
        in.readBytes(contentBytes);
        String content = new String(contentBytes, StandardCharsets.UTF_8);
        out.add(new DeviceMessage(type, content));
    }
}