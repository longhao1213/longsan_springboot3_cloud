package com.longsan.simulation.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 解码器
 */
public class DeviceDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 5) return; // 最小长度：1(type)+4(length)
        in.markReaderIndex();
        byte type = in.readByte(); // 读取1字节消息类型
        int length = in.readInt(); // 读取4字节内容长度

        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }
        byte[] contentBytes = new byte[length]; // 读取正文
        in.readBytes(contentBytes);
        String content = new String(contentBytes, StandardCharsets.UTF_8);
        out.add(new DeviceMessage(type, content));
    }
}