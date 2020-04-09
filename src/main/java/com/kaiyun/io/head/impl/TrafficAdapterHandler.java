package com.kaiyun.io.head.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: trafficdataserver
 * @description: 用于处理接收到的消息，ChannelInboundHandlerAdapter在时间节点上不释放消息，将消息传递给下一个ChannelHandler处理
 * 重写channelRead方法之后，回写数据只能回写原数据（可以说是Echo协议？？？），否则失效。并且回写之后，channel会关闭。
 * @author: Chonghao Zhong
 * @create: 2020-03-12 14:58
 **/
public class TrafficAdapterHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(TrafficAdapterHandler.class);

    /**
     * 注册事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("{}客户端已连接", ctx.name());
        super.channelRegistered(ctx);
    }

    /**
     * 关闭事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("{}客户端关闭", ctx.name());
        super.channelInactive(ctx);
    }

    /**
     * 异常通知事件
     * 捕获到异常时调用
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("{}客户端异常", ctx.name());
        cause.printStackTrace();
        ctx.close();
    }
}
