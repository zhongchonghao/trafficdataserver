package com.kaiyun.io.head.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @program: trafficdataserver
 * @description: 心跳检测
 * @author: Chonghao Zhong
 * @create: 2020-03-12 16:37
 **/
public class TrafficIdleStateHandler extends IdleStateHandler {
    private static Logger logger = LoggerFactory.getLogger(TrafficIdleStateHandler.class);

    private static final int READER_IDLE_TIME = 60;

    public TrafficIdleStateHandler() {
        /*
        long readerIdleTime:读空闲超时时间设定，如果channelRead()方法超过readerIdleTime时间未被调用则会触发超时事件调用userEventTrigger()方法。
        long writerIdleTime:写空闲超时时间设定，如果write()方法超过writerIdleTime时间未被调用则会触发超时事件调用userEventTrigger()方法。
        long allIdleTime:所有类型的空闲超时时间设定，包括读空闲和写空闲。
        TimeUnit unit:时间单位，包括时分秒等。
         */
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        logger.info("{}秒内未读到数据，关闭{}连接", READER_IDLE_TIME, ctx.name());
        ctx.channel().close();
    }
}
