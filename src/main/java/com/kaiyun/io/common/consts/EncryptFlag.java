package com.kaiyun.io.common.consts;

/**
 * @program: trafficdataserver
 * @description: 报文数据体是否加密
 * @author: Chonghao Zhong
 * @create: 2020-04-01 15:22
 **/
public interface EncryptFlag {
    /**
     * 加密
     */
    byte YES = 0x01;
    /**
     * 不加密
     */
    byte NO = 0x00;
}
