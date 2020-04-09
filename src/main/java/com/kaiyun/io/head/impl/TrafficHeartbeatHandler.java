package com.kaiyun.io.head.impl;

import com.kaiyun.io.common.consts.BusinessDataType;
import com.kaiyun.io.common.consts.EncryptFlag;
import com.kaiyun.io.common.consts.MsgSn;
import com.kaiyun.io.common.consts.UserInfo;
import com.kaiyun.io.packet.BasePacket;
import com.kaiyun.io.packet.TrafficHeartbeatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: trafficdataserver
 * @description: 心跳包发送
 * @author: Chonghao Zhong
 * @create: 2020-03-19 16:51
 **/
public class TrafficHeartbeatHandler extends SimpleChannelInboundHandler<TrafficHeartbeatResponsePacket> {
    private static Logger logger = LoggerFactory.getLogger(TrafficHeartbeatHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TrafficHeartbeatResponsePacket msg) {
        msg.setMsgLength(BasePacket.getFixedByteLength());
        msg.setMsgSn(MsgSn.getMsgSN());
        msg.setMsgId(BusinessDataType.UP_LINKTEST_RSP);
        msg.setMsgGNSSCenterId(UserInfo.MSG_GNSSCENTERID);
        msg.setVersionFlag(new byte[]{1, 0, 0});
        msg.setEncryptFlag(EncryptFlag.NO);
        msg.setEncryptKey(0);

        logger.info("心跳包响应内容：{}", msg);
        ctx.channel().writeAndFlush(msg);
    }
}
