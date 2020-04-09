package com.kaiyun.io.common.consts;

/**
 * @program: trafficdataserver
 * @description: 业务数据类型标志
 * @author: Chonghao Zhong
 * @create: 2020-04-01 15:17
 **/
public interface BusinessDataType {
    /**
     * 主链路登录请求消息
     */
    short UP_CONNECT_REQ = 0x1001;
    /**
     * 主链路登录应答消息
     */
    short UP_CONNECT_RSP = 0x1002;
    /**
     * 主链路连接保持请求消息
     */
    short UP_LINKTEST_REQ = 0x1005;
    /**
     * 主链路连接保持应答消息
     */
    short UP_LINKTEST_RSP = 0x1006;
    /**
     * 主链路动态信息交换消息
     */
    short UP_EXG_MSG = 0x1200;
}
