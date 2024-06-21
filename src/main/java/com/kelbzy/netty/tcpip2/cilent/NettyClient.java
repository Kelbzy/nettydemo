package com.kelbzy.netty.tcpip2.cilent;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author by kelbzy
 * @Classname NettyClient
 * @Description TODO
 * @Date 2024/6/18
 */
public class NettyClient {
    //网络客户端
    public static void main(String[] args) throws Exception{
        //创建一个线程组(不像服务端需要有连接等待的线程池)
        EventLoopGroup group = new NioEventLoopGroup();
        //创建客户端的服务启动助手完成相应配置
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                //创建一个通道初始化对象
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyClientHandler());//往pipeline中添加自定义的handler
                    }
                });
        System.out.println("客户端已运行");
        //启动客户端去连接服务器端(通过启动助手)
        ChannelFuture cf = bootstrap.connect("127.0.0.1", 8888).sync();
        //关闭连接(异步非阻塞)
        cf.channel().closeFuture().sync();

    }
}


