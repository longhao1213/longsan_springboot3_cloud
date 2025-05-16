package com.longsan.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {

    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    /**
     * 启动流程
     */
    public void run() {
        // boss线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 工作线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try  {
            // 启动程序
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    // 指定管道
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("初始化");
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            System.out.println("echo服务启动中");
            // 绑定端口，同步等待成功
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

            // 等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new EchoServer(port).run();
    }
}
