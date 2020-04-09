package com.kaiyun.io.head.impl;

import com.kaiyun.io.common.consts.LoginResponseCode;
import com.kaiyun.io.common.consts.UserInfo;
import com.kaiyun.io.packet.TrafficLoginRequestPacket;
import com.kaiyun.io.packet.TrafficLoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: trafficdataserver
 * @description: 登录适配
 * @author: Chonghao Zhong
 * @create: 2020-03-13 09:09
 **/
public class TrafficLoginHandler extends SimpleChannelInboundHandler<TrafficLoginRequestPacket> {
    private static Logger logger = LoggerFactory.getLogger(TrafficLoginHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TrafficLoginRequestPacket msg) {
        logger.info("登录报文{}", msg);
        TrafficLoginResponsePacket trafficLoginResponsePacket = new TrafficLoginResponsePacket();
        byte loginResponseCode = valid(msg);
        trafficLoginResponsePacket.setResul(loginResponseCode);
        trafficLoginResponsePacket.setVerifyCode(0);
        // 登录响应
        logger.info("登录响应：{}", trafficLoginResponsePacket);
        ctx.channel().writeAndFlush(trafficLoginResponsePacket);
    }

    /**
     * 用户名密码校验
     */
    private byte valid(TrafficLoginRequestPacket msg) {
        int userId = msg.getUserId();
        String password = msg.getPassword();
        logger.info("接收到了登录的请求->用户名：{};密码：{}", userId, password);

        if (UserInfo.USER_ID != userId) {
            logger.info("USER_ID不正确");
            return LoginResponseCode.USERNAME_ERROR;
        }
        if (!UserInfo.PASSWORD.equals(password)) {
            logger.info("PASSWORD_ERROR");
            return LoginResponseCode.PASSWORD_ERROR;
        }

        logger.info("登录验证成功");
        return LoginResponseCode.SUCCESS;
    }
}
