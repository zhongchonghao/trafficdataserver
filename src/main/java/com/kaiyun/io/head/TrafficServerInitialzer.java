package com.kaiyun.io.head;

import com.kaiyun.io.head.impl.*;
import com.kaiyun.io.protocol.decoder.TrafficDecoderAdapter;
import com.kaiyun.io.protocol.encode.TrafficEncodeAdapter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * @program: trafficdataserver
 * @description: handler初始化操作
 * @author: Chonghao Zhong
 * @create: 2020-03-12 16:18
 **/
public class TrafficServerInitialzer extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) {
        // ChannelPipeline用于存放管理ChannelHandel
        // ChannelHandler用于处理请求响应的业务逻辑相关代码

        // 分隔符
        ByteBuf delimiter = Unpooled.copiedBuffer(new byte[]{0x5d});
        // 分隔符解码器 根据约定好的数据确定是否add
        nioSocketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, false, delimiter));
        // 心跳检测
        nioSocketChannel.pipeline().addLast(new TrafficIdleStateHandler());
        // 客户端连接
        nioSocketChannel.pipeline().addLast(new TrafficAdapterHandler());
        // 解码
        nioSocketChannel.pipeline().addLast(new TrafficDecoderAdapter());
        // 心跳包发送
        nioSocketChannel.pipeline().addLast(new TrafficHeartbeatHandler());
        // 登录
        nioSocketChannel.pipeline().addLast(new TrafficLoginHandler());
        // GPS
        nioSocketChannel.pipeline().addLast(new TrafficVehicleLocationHandler());
        // 编码
        nioSocketChannel.pipeline().addLast(new TrafficEncodeAdapter());
    }
}
