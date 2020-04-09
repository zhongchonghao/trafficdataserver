package com.kaiyun.io.packet;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @program: trafficdataserver
 * @description: 登录包
 * @author: Chonghao Zhong
 * @create: 2020-03-12 17:39
 **/
@Getter
@Setter
@ToString
public class TrafficLoginRequestPacket extends BasePacket {
    /**
     * id 4字节
     */
    private int userId;
    /**
     * 密码 8字节
     */
    private String password;
    /**
     * 下级平台IP 32字节
     */
    private String downLinkIp;
    /**
     * 下级平台端口 2字节
     */
    private short downLinkPort;

    @Override
    public byte[] getMsgBodyByteArr() {
        return new byte[0];
    }
}
