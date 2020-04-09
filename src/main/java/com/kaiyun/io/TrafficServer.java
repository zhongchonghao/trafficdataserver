package com.kaiyun.io;

import com.kaiyun.io.head.TrafficServerInitialzer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: trafficdataserver
 * @description: Netty服务端
 * @author: Chonghao Zhong
 * @create: 2020-03-12 14:38
 **/
public class TrafficServer {
    private static Logger logger = LoggerFactory.getLogger(TrafficServer.class);

    private final int port;

    public TrafficServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        // Group：群组，Loop：循环，Event：事件。
        // Netty内部都是通过线程在处理各种数据，EventLoopGroup就是用来管理调度他们的，注册Channel，管理他们的生命周期。
        // NioEventLoopGroup是一个处理I/O操作的多线程事件循环
        // bossGroup作为boss,接收传入连接
        // 因为bossGroup仅接收客户端连接，不做复杂的逻辑处理，为了尽可能减少资源的占用，取值越小越好
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // workerGroup作为worker，处理boss接收的连接的流量和将接收的连接注册进入这个worker
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // ServerBootstrap负责建立服务端
            // 可以直接使用Channel去建立服务端，但是大多数情况下你无需做这种乏味的事情
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    // 指定使用NioServerSocketChannel产生一个Channel用来接收连接
                    .channel(NioServerSocketChannel.class)
                    // 用于向Channel当中添加ChannelInboundHandler的实现
                    .childHandler(new TrafficServerInitialzer())
                    // 对Channel进行一些配置
                    // 用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 监控连接是否有效，当连接处于空闲状态的时候，超过了2个小时，本地的TCP实现会发送一个数据包给远程的 socket，如果远程没有发回响应，TCP会持续尝试11分钟，知道响应为止，如果在12分钟的时候还没响应，TCP尝试关闭socket连接。
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定的服务器;sync 等待服务器关闭
            ChannelFuture future = serverBootstrap.bind(port).sync();
            logger.info(TrafficServer.class.getName() + " started and listen on " + future.channel().localAddress());
            // sync()会同步等待连接操作结果，用户线程将在此wait()，直到连接操作完成之后，线程被notify(),用户代码继续执行
            // closeFuture()当Channel关闭时返回一个ChannelFuture,用于链路检测
            future.channel().closeFuture().sync();
        } finally {
            // 关闭 EventLoopGroup，释放所有资源。
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8095;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        // 设计端口、启动服务器
        new TrafficServer(port).start();
    }
}
