package com.kaiyun.io.protocol.decoder.factory;

import com.kaiyun.io.common.consts.EncryptFlag;
import com.kaiyun.io.common.util.PacketDecoderUtils;
import com.kaiyun.io.packet.BasePacket;
import com.kaiyun.io.packet.TrafficLoginRequestPacket;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @program: trafficdataserver
 * @description: 登录解码
 * @author: Chonghao Zhong
 * @create: 2020-03-13 15:17
 **/
public class LoginDecoder implements Decoder {
    private static Logger logger = LoggerFactory.getLogger(LoginDecoder.class);

    @Override
    public BasePacket decoder(byte[] bytes) throws Exception {
        TrafficLoginRequestPacket loginRequestPacket = new TrafficLoginRequestPacket();
        ByteBuf byteBuf = PacketDecoderUtils.baseDecoder(bytes, loginRequestPacket);
        loginPacketDecoder(byteBuf, loginRequestPacket);
        return loginRequestPacket;
    }

    /**
     * @param byteBuf
     * @param loginRequestPacket
     */
    private void loginPacketDecoder(ByteBuf byteBuf, TrafficLoginRequestPacket loginRequestPacket) {
        ByteBuf msgBodyBuf = null;
        if (loginRequestPacket.getEncryptFlag() == EncryptFlag.NO) {
            logger.info("报文未加密！继续处理。");
            msgBodyBuf = PacketDecoderUtils.getMsgBodyBuf(byteBuf);
            logger.info("msgBodyBuf====" + msgBodyBuf);
        } else {
            // TODO: 后续处理
            logger.info("报文已加密！未处理。");
            return;
        }

        loginRequestPacket.setUserId(msgBodyBuf.readInt());

        byte[] passwordBytes = new byte[8];
        msgBodyBuf.readBytes(passwordBytes);
        loginRequestPacket.setPassword(new String(passwordBytes, Charset.forName("GBK")).trim());

        // TODO ip和端口号的解析待确定
        byte[] downLinkIpBytes = new byte[32];
        msgBodyBuf.readBytes(downLinkIpBytes);
        loginRequestPacket.setDownLinkIp(new String(downLinkIpBytes));

        loginRequestPacket.setDownLinkPort(msgBodyBuf.readShort());
    }
}
