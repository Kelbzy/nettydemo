package com.kelbzy.netty.tcpip.cilent;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    ClientChannelHandler clientChannelHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("idleStateHandler",
                new IdleStateHandler(15,0,0, TimeUnit.MINUTES));
        pipeline.addLast(new StringDecoder(),new StringEncoder());
        pipeline.addLast("clientChannelHandler",clientChannelHandler);
    }
}

