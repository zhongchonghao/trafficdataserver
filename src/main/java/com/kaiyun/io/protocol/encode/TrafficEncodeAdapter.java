package com.kaiyun.io.protocol.encode;

import com.kaiyun.io.common.util.CommonUtils;
import com.kaiyun.io.common.util.PacketDecoderUtils;
import com.kaiyun.io.common.util.PacketEncoderUtils;
import com.kaiyun.io.packet.BasePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: trafficdataserver
 * @description: 编码器
 * @author: Chonghao Zhong
 * @create: 2020-03-19 11:41
 **/
public class TrafficEncodeAdapter extends MessageToByteEncoder<BasePacket> {
    private static Logger logger = LoggerFactory.getLogger(TrafficEncodeAdapter.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, BasePacket msg, ByteBuf out) {
        logger.debug("进行报文发送处理");
        byte[] allBody = msg.getAllBody();
        // 转义
        byte[] dataBytes = PacketEncoderUtils.encoderEscape(allBody);
        byte[] bytes1 = CommonUtils.append(new byte[]{BasePacket.HEAD_FLAG}, dataBytes);
        byte[] bytes = CommonUtils.append(bytes1, new byte[]{BasePacket.END_FLAG});
        String hexStr = PacketDecoderUtils.bytes2HexStr(bytes);
        logger.info("发出的报文为：{}", hexStr);
        out.writeBytes(bytes);
    }
}
