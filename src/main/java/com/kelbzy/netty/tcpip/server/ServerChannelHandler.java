package com.kelbzy.netty.tcpip.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class ServerChannelHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerChannelHandler.class);
    /**
     * 拿到传过来的msg数据，开始处理
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        LOGGER.info("Netty tcp server receive message: {}",msg);
        channelHandlerContext.writeAndFlush(" response message "+msg).syncUninterruptibly();
    }

    /**
     * 活跃的、有效的通道
     * 第一次连接成功后进入的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        LOGGER.info("tcp client "+getRemoteAddress(ctx)+" connect success");
        NettyTcpServer.map.put(getIPString(ctx),ctx.channel());
    }

    /**
     * 不活动的通道
     * 连接丢失后执行的方法（client端可据此实现断线重连）
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 删除Channel Map中失效的Client
        NettyTcpServer.map.remove(getIPString(ctx));
        ctx.close();
    }

    /**
     * 异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        // 发生异常 关闭连接
        LOGGER.error("引擎{}的通道发生异常，断开连接",getRemoteAddress(ctx));
        ctx.close();
    }

    /**
     * 心跳机制 超时处理
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        String socketString = ctx.channel().remoteAddress().toString();
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state()== IdleState.READER_IDLE) {
                LOGGER.info("Client: "+socketString+" READER_IDLE读超时");
                ctx.disconnect();
            }else if (event.state()==IdleState.WRITER_IDLE){
                LOGGER.info("Client: "+socketString+" WRITER_IDLE写超时");
                ctx.disconnect();
            }else if (event.state()==IdleState.ALL_IDLE){
                LOGGER.info("Client: "+socketString+" ALL_IDLE总超时");
                ctx.disconnect();
            }
        }
    }

    /**
     * 获取client对象：ip+port
     * @param channelHandlerContext
     * @return
     */
    public String getRemoteAddress(ChannelHandlerContext channelHandlerContext){
        String socketString = "";
        socketString = channelHandlerContext.channel().remoteAddress().toString();
        return socketString;
    }

    /**
     * 获取client的ip
     * @param channelHandlerContext
     * @return
     */
    public String getIPString(ChannelHandlerContext channelHandlerContext){
        String ipString = "";
        String socketString = channelHandlerContext.channel().remoteAddress().toString();
        int colonAt = socketString.indexOf(":");
        ipString = socketString.substring(1,colonAt);
        return ipString;
    }
}

