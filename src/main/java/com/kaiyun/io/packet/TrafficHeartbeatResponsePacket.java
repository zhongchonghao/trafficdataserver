package com.kaiyun.io.packet;

/**
 * @program: trafficdataserver
 * @description: 心跳应答包 数据体为空
 * @author: Chonghao Zhong
 * @create: 2020-03-19 16:54
 **/
public class TrafficHeartbeatResponsePacket extends BasePacket {
    @Override
    public byte[] getMsgBodyByteArr() {
        return new byte[0];
    }
}
