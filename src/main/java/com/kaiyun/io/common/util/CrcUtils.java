package com.kaiyun.io.common.util;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @program: trafficdataserver
 * @description: 校验
 * @author: Chonghao Zhong
 * @create: 2020-03-13 15:11
 **/
public class CrcUtils {
    private static Logger logger = LoggerFactory.getLogger(CrcUtils.class);

    public static boolean checkCRC(byte[] bytes) {
        ByteBuf byteBuf = CommonUtils.getByteBuf(bytes);
        // 获取数据长度和crc标示
        byteBuf.skipBytes(1);
        int msgLength = CommonUtils.bytes2int(new byte[]{bytes[1], bytes[2], bytes[3], bytes[4]});
        int crcLength = msgLength - 4;
        byte[] crcBody = new byte[crcLength];
        byteBuf.readBytes(crcBody);

        short oldCRCcode = byteBuf.readShort();
        short currentCRCcode = getCRC16(crcBody);
        if (oldCRCcode == currentCRCcode) {
            return true;
        }
        logger.info("crc校验失败;报文信息：{}" + Arrays.toString(bytes));
        return false;
    }

    public static short getCRC16(byte[] bytes) {
        short crc = (short) 0xffff;
        short polynomial = 0x1021;
        for (int index = 0; index < bytes.length; index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) {
                    crc ^= polynomial;
                }
            }
        }
        crc &= 0xffff;
        return crc;
    }
}
