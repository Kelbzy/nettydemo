package com.kelbzy.netty.tcpip;


import com.kelbzy.netty.tcpip.cilent.NettyTcpClient;
import com.kelbzy.netty.tcpip.server.NettyTcpServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartApplication implements CommandLineRunner {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(StartApplication.class, args);
    }

    @Autowired
    NettyTcpServer nettyTcpServer;

    @Autowired
    NettyTcpClient nettyTcpClient;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        ChannelFuture start = nettyTcpServer.start();
        nettyTcpClient.connect();
//        for (int i = 0; i < 10; i++) {
//            nettyTcpClient.sendMessage("hello world "+i);
//        }
        start.channel().closeFuture().syncUninterruptibly();
    }
}

