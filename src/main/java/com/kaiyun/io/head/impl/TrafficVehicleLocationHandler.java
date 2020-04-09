package com.kaiyun.io.head.impl;

import com.kaiyun.io.bean.dto.TrafficVehicleLocationDTO;
import com.kaiyun.io.packet.TrafficVehicleLocationPacket;
import com.kaiyun.io.thread.ThreadPoolFactory;
import com.kaiyun.io.thread.VehicleLocationThread;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: trafficdataserver
 * @description: 车辆定位信息
 * @author: Chonghao Zhong
 * @create: 2020-03-16 15:08
 **/
public class TrafficVehicleLocationHandler extends SimpleChannelInboundHandler<TrafficVehicleLocationPacket> {
    private static Logger logger = LoggerFactory.getLogger(TrafficVehicleLocationHandler.class);

    private static ThreadPoolExecutor executor = ThreadPoolFactory.newSynchronousQueueExecutor(256);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TrafficVehicleLocationPacket msg) throws ParseException {
        logger.info("车辆定位信息：{}", msg);
        // 获取DTO对象
        TrafficVehicleLocationDTO trafficVehicleLocationDTO = new TrafficVehicleLocationDTO(msg);
        // 执行持久化
        executor.execute(new VehicleLocationThread(trafficVehicleLocationDTO));
    }
}
