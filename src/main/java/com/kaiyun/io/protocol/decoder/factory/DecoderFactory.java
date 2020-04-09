package com.kaiyun.io.protocol.decoder.factory;

import com.kaiyun.io.common.consts.BusinessDataType;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: trafficdataserver
 * @description: 解码工厂类
 * @author: Chonghao Zhong
 * @create: 2020-03-13 15:15
 **/
public class DecoderFactory {
    private static DecoderFactory factory = new DecoderFactory();
    private static Map<Short, Decoder> DECODER_FACTORY = new HashMap<>();

    static {
        DECODER_FACTORY.put(BusinessDataType.UP_CONNECT_REQ, new LoginDecoder());
        DECODER_FACTORY.put(BusinessDataType.UP_LINKTEST_REQ, new HeartbeatDecoder());
        DECODER_FACTORY.put(BusinessDataType.UP_EXG_MSG, new VehicleLocationDecoder());
    }

    /**
     * 对外暴露请求入口
     *
     * @return 工厂模型
     */
    public static DecoderFactory getInstance() {
        return factory;
    }

    /**
     * 选取具体的解码类型
     *
     * @param businessDataType 业务数据类型标志
     * @return 具体的解码器
     */
    public Decoder creator(short businessDataType) {
        return DECODER_FACTORY.get(businessDataType);
    }
}
