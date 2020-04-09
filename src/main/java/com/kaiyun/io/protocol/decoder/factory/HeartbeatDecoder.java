package com.kaiyun.io.protocol.decoder.factory;

import com.kaiyun.io.packet.BasePacket;
import com.kaiyun.io.packet.TrafficHeartbeatResponsePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: trafficdataserver
 * @description: 心跳解码器
 * @author: Chonghao Zhong
 * @create: 2020-03-24 15:58
 **/
public class HeartbeatDecoder implements Decoder {
    private static Logger logger = LoggerFactory.getLogger(HeartbeatDecoder.class);

    @Override
    public BasePacket decoder(byte[] bytes) {
        logger.debug("心跳解码器！");
        return new TrafficHeartbeatResponsePacket();
    }
}
