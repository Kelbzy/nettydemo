package com.kelbzy.netty.tcpip2.cilent;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.kelbzy.netty.tcpip.cilent.ClientChannelHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        LOGGER.info("接收服务器端数据 " + byteBuf.toString(CharsetUtil.UTF_8));
    }

}
