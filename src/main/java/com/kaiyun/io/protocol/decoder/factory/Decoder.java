package com.kaiyun.io.protocol.decoder.factory;

import com.kaiyun.io.packet.BasePacket;

/**
 * @program: trafficdataserver
 * @description:
 * @author: Chonghao Zhong
 * @create: 2020-03-13 15:17
 **/
public interface Decoder {
    /**
     *
     * @param bytes
     * @return
     */
    BasePacket decoder(byte[] bytes) throws Exception;
}
