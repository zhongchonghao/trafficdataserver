package com.kaiyun.io.common.consts;

/**
 * @program: trafficdataserver
 * @description: 主链路登录应答
 * @author: Chonghao Zhong
 * @create: 2020-04-01 15:21
 **/
public interface LoginResponseCode {
    /**
     * 0x00:成功;
     */
    byte SUCCESS = 0x00;
    /**
     * 0x01:IP地址不正确；
     */
    byte IP_ERROR = 0x01;
    /**
     * 0x02:接入码不正确；
     */
    byte ACCESS_CODE_ERROR = 0x02;
    /**
     * 0x03:用户没用注册；
     */
    byte USERNAME_ERROR = 0x03;
    /**
     * 0x04:密码错误;
     */
    byte PASSWORD_ERROR = 0x04;
    /**
     * 0x05:资源紧张，稍后再连接(已经占用）；
     */
    byte RESOURCE_CRISIS = 0x05;
    /**
     * 0x06：其他。
     */
    byte OTHER_ERROR = 0x06;
}
