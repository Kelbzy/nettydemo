package com.kelbzy.netty.tcpip2.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServerDemo {
    public static void main(String[] args) throws InterruptedException {
        NettyServerDemo.start();
    }

    public static void start() throws InterruptedException {
        //其中一个是处理网络连接，另一个是线程组是处理客户端的链接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        //启动辅助类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //设置两个线程组
        serverBootstrap.group(bossGroup,workGroup)
                //精华部分，设置通道的底层实现，通过NioServerSocketChannel，这也是Netty的与NIO搭配的地方(此处作为服务器端通道的实现)
                .channel(NioServerSocketChannel.class)
                //这套机制是在2个小时左右没有接到上层链接时激活
                .option(ChannelOption.SO_KEEPALIVE,true)
                //是否启用心跳保活机制。在双方TCP套接字建立连接后（即都进入ESTABLISHED状态）
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    //创建一个通道初始化对象
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //此处添加规则,自定义的
                        socketChannel.pipeline().addLast(new ServerHandler());
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
        boolean active = channelFuture.channel().isActive();
        if (active){
            System.out.println("-----服务器启动成功-----");
        }
    }

}
