package com.kelbzy.netty.tcpip.cilent;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//import javax.xml.ws.Holder;

@Component
public class NettyTcpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyTcpClient.class);

    @Value("${netty.tcp.server.host}")
    String HOST;

    @Value("${netty.tcp.server.port}")
    int PORT;

    @Autowired
    ClientChannelInitializer clientChannelInitializer;

    private Channel channel;

    /**
     * 初始化 `Bootstrap` 客户端引导程序
     * @return
     */
    private final Bootstrap getBootstrap(){
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)//通道连接者
                .handler(clientChannelInitializer)//通道处理者
                .option(ChannelOption.SO_KEEPALIVE,true);// 心跳报活
        return bootstrap;
    }

    /**
     *  建立连接，获取连接通道对象
     */
    public void connect(){
        ChannelFuture channelFuture = getBootstrap().connect(HOST, PORT).syncUninterruptibly();
        if (channelFuture != null&&channelFuture.isSuccess()) {
            channel = channelFuture.channel();
            LOGGER.info("connect tcp server host = {},port = {} success", HOST,PORT);
        }else {
            LOGGER.error("connect tcp server host = {},port = {} fail",HOST,PORT);
        }
    }

    /**
     * 向服务器发送消息
     */
    public void sendMessage(Object msg) throws InterruptedException {
        if (channel != null) {
            channel.writeAndFlush(msg).sync();
        }else {
            LOGGER.warn("消息发送失败，连接尚未建立");
        }
    }
}


