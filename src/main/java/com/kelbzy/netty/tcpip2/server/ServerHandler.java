package com.kelbzy.netty.tcpip2.server;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author kelbzy
 * @Classname ServerHandler
 * @Description TODO 服务器中的业务处理类
 * @Date 2024/6/18
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("已连接");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("进入channelInactive");
        super.channelInactive(ctx);
    }

    /**
     *读取传输来的数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf byteBuf = Unpooled.buffer(600);
//        byteBuf.writeBytes((ByteBuf) msg);

        ByteBuf byteBuf = (ByteBuf) msg;
//        String data = byteBuf.toString(CharsetUtil.UTF_8);
//        System.out.println("接收客户端数据: " + data);
//        System.out.println("接收客户端数据" +msg);

//        System.out.println("接收客户端数据" + byteBuf.toString(Charset.defaultCharset()));
        System.out.println("接收客户端数据" + byteBuf.toString(CharsetUtil.UTF_8));

        super.channelRead(ctx, msg);
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("进入channelReadComplete");
//        super.channelReadComplete(ctx);
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("进入exceptionCaught");
        super.exceptionCaught(ctx, cause);
    }
}


