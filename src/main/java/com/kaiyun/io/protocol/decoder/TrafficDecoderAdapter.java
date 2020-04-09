package com.kaiyun.io.protocol.decoder;

import com.kaiyun.io.common.util.CommonUtils;
import com.kaiyun.io.common.util.CrcUtils;
import com.kaiyun.io.common.util.PacketDecoderUtils;
import com.kaiyun.io.packet.BasePacket;
import com.kaiyun.io.protocol.decoder.factory.DecoderFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @program: trafficdataserver
 * @description: 解码器
 * @author: Chonghao Zhong
 * @create: 2020-03-13 09:47
 **/
public class TrafficDecoderAdapter extends ByteToMessageDecoder {
    private static Logger logger = LoggerFactory.getLogger(TrafficDecoderAdapter.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        logger.info("解码器接收到数据: {}", ByteBufUtil.hexDump(in));
        //判断是否有可读的字节
        if (in.readableBytes() <= 0) {
            return;
        }
        // 1、进行转义
        byte[] bytes = PacketDecoderUtils.decoderEscape(in);

        // 2、校验crc
        if (!CrcUtils.checkCRC(bytes)) {
            return;
        }
        // 3、判断是那种类型的数据，交给具体的解码器类完成。
        ByteBuf byteBuf = CommonUtils.getByteBuf(bytes);
        byteBuf.skipBytes(9);
        // 获取业务标志
        short msgId = byteBuf.readShort();

        // 交给具体的解码器
        BasePacket packet = null;
        try {
            packet = DecoderFactory.getInstance().creator(msgId).decoder(bytes);
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                // 没有可用的解析器，忽略这条信息！此信息不在业务范围内。
                logger.info("没有可用的解析器，忽略这条信息！此信息不在业务范围内。");
            } else {
                logger.error("报文解析出错！错误信息：{}；报文信息：{}；", e.getMessage(), Arrays.toString(bytes));
            }
            return;
        }
        out.add(packet);
    }
}
