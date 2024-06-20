package com.kelbzy.netty.tcpip.cilent;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class ClientChannelHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientChannelHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        LOGGER.info("Netty tcp client receive msg : " + msg);
    }

}

