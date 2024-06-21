package com.kelbzy.netty.tcpip2.cilent;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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

        String receivedData = byteBuf.toString(CharsetUtil.UTF_8);

        LOGGER.info("接收服务器端数据: " + receivedData);

        // 在这里添加回应消息的代码
        String response = "i have recived the news: " + receivedData;

        ByteBuf responseByteBuf = Unpooled.copiedBuffer(response, CharsetUtil.UTF_8);

        channelHandlerContext.writeAndFlush(responseByteBuf);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 发送初始消息到服务端
        String initialMsg = "connection builded！！！";

        ctx.writeAndFlush(Unpooled.copiedBuffer(initialMsg, CharsetUtil.UTF_8));
    }
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
//        ByteBuf byteBuf = (ByteBuf) msg;
//        LOGGER.info("接收服务器端数据 " + byteBuf.toString(CharsetUtil.UTF_8));
//    }
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        //发送消息到服务端
//        ctx.writeAndFlush(Unpooled.copiedBuffer("歪比巴卜~茉莉~Are you good~马来西亚~", CharsetUtil.UTF_8));
//    }

}
