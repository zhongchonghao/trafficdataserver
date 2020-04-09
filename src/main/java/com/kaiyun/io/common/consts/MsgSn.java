package com.kaiyun.io.common.consts;

/**
 * @program: trafficdataserver
 * @description: 报文序列号
 * @author: Chonghao Zhong
 * @create: 2020-04-01 15:16
 **/
public class MsgSn {
    /**
     * 报文序列号
     */
    private static int msg_sn = -1;

    public static int getMsgSN() {
        msg_sn += 1;
        return msg_sn;
    }
}
