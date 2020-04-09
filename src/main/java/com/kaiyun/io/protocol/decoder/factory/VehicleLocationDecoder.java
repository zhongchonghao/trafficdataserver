package com.kaiyun.io.protocol.decoder.factory;

import com.kaiyun.io.common.consts.EncryptFlag;
import com.kaiyun.io.common.consts.SubBusinessDataType;
import com.kaiyun.io.common.util.PacketDecoderUtils;
import com.kaiyun.io.packet.BasePacket;
import com.kaiyun.io.packet.TrafficVehicleLocationPacket;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @program: trafficdataserver
 * @description: GPS解码
 * @author: Chonghao Zhong
 * @create: 2020-03-24 16:04
 **/
public class VehicleLocationDecoder implements Decoder {
    private static Logger logger = LoggerFactory.getLogger(VehicleLocationDecoder.class);

    @Override
    public BasePacket decoder(byte[] bytes) throws Exception {
        TrafficVehicleLocationPacket trafficVehicleLocationPacket = new TrafficVehicleLocationPacket();
        ByteBuf byteBuf = PacketDecoderUtils.baseDecoder(bytes, trafficVehicleLocationPacket);
        packetDecoder(byteBuf, trafficVehicleLocationPacket);
        return trafficVehicleLocationPacket;
    }

    private void packetDecoder(ByteBuf byteBuf, TrafficVehicleLocationPacket packet) throws Exception {
        ByteBuf msgBodyBuf = null;
        if (packet.getEncryptFlag() == EncryptFlag.NO) {
            msgBodyBuf = PacketDecoderUtils.getMsgBodyBuf(byteBuf);
        } else {
            logger.info("报文已加密！未处理。报文信息：{}", packet);
            return;
        }
        // 车牌号
        byte[] vehicleNoBytes = new byte[21];
        msgBodyBuf.readBytes(vehicleNoBytes);
        packet.setVehicleNo(new String(vehicleNoBytes, Charset.forName("GBK")).trim());
        // 车辆颜色
        packet.setVehicleColor(msgBodyBuf.readByte());
        // 子业务类型标识
        packet.setDataType(msgBodyBuf.readShort());
        // 如果不是定位的数据，抛出空指针错误,解码适配器会对空指针错误做处理。
        if (packet.getDataType() != SubBusinessDataType.UP_EXG_MSG_REAL_LOCATION) {
            throw new NullPointerException();
        }
        // 后续数据长度
        packet.setDataLength(msgBodyBuf.readInt());
        // 经纬度信息是否按国标进行加密
        packet.setExcrypt(msgBodyBuf.readByte());
        if (packet.getExcrypt() == EncryptFlag.YES) {
            logger.info("报文已加密！未处理。报文信息：{}", packet);
        }
        // 跳过时间
//        msgBodyBuf.skipBytes(7);
        int day = Byte.toUnsignedInt(msgBodyBuf.readByte());
        int month = Byte.toUnsignedInt(msgBodyBuf.readByte());
        packet.setDate(LocalDate.of(msgBodyBuf.readShort(), month, day));
        packet.setTime(LocalTime.of(Byte.toUnsignedInt(msgBodyBuf.readByte()), Byte.toUnsignedInt(msgBodyBuf.readByte()), Byte.toUnsignedInt(msgBodyBuf.readByte())));
        // 经纬度
        packet.setLon(msgBodyBuf.readInt());
        packet.setLat(msgBodyBuf.readInt());
        // 速度
        packet.setVec1(msgBodyBuf.readShort());
        // 行驶记录速度
        packet.setVec2(msgBodyBuf.readShort());
        // 车辆当前总里程数
        packet.setVec3(msgBodyBuf.readInt());
        // 方向
        packet.setDirection(msgBodyBuf.readShort());
        // 海拔
        packet.setAltitude(msgBodyBuf.readShort());
        // 车辆状态
        packet.setState(msgBodyBuf.readInt());
        // 报警状态
        packet.setAlarm(msgBodyBuf.readInt());
    }
}
