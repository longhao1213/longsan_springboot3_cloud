package com.longsan.simulation.client;

import com.longsan.simulation.server.DeviceDecoder;
import com.longsan.simulation.server.DeviceEncoder;
import com.longsan.simulation.server.DeviceMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MockEmbeddedDevice {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new DeviceEncoder());
                            p.addLast(new DeviceDecoder());
                            p.addLast(new SimpleChannelInboundHandler<DeviceMessage>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, DeviceMessage msg) {
                                    System.out.println("收到服务端回复：" + msg.getContent());
                                }
                            });
                        }
                    });

            Channel ch = b.connect("127.0.0.1", 9000).sync().channel();

            // 注册设备ID（type = 0）
            String deviceId = "device-001";
            ch.writeAndFlush(new DeviceMessage((byte) 0, deviceId));

            // 模拟心跳
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> {
                ch.writeAndFlush(new DeviceMessage((byte) 1, "heartbeat-" + System.currentTimeMillis()));
            }, 0, 5, TimeUnit.SECONDS);

            // 模拟数据上报
            for (int i = 0; i < 3; i++) {
                ch.writeAndFlush(new DeviceMessage((byte) 2, "识别数据上报：" + i));
                Thread.sleep(3000);
            }
            for (int i = 0; i < 5; i++) {
                // 连续发送 5 条数据
                ch.writeAndFlush(new DeviceMessage((byte) 2, "测试数据包-" + i));
            }

            Thread.sleep(20000);
            ch.close();
        } finally {
            group.shutdownGracefully();
        }
    }
}