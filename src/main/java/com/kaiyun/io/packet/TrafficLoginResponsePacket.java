package com.kaiyun.io.packet;

import com.kaiyun.io.common.consts.BusinessDataType;
import com.kaiyun.io.common.consts.EncryptFlag;
import com.kaiyun.io.common.consts.MsgSn;
import com.kaiyun.io.common.consts.UserInfo;
import com.kaiyun.io.common.util.CommonUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @program: trafficdataserver
 * @description: 登录响应包
 * @author: Chonghao Zhong
 * @create: 2020-03-13 15:51
 **/
@Getter
@Setter
@ToString
public class TrafficLoginResponsePacket extends BasePacket {
    private static final int FIXED_LENGTH = 5;

    /**
     * 标志 1位
     */
    private byte resul;
    /**
     * 校验码 4字节
     */
    private int verifyCode;

    public TrafficLoginResponsePacket() {
        setMsgLength(getFixedByteLength() + FIXED_LENGTH);
        setMsgSn(MsgSn.getMsgSN());
        setMsgId(BusinessDataType.UP_CONNECT_RSP);
        setMsgGNSSCenterId(UserInfo.MSG_GNSSCENTERID);
        setVersionFlag(new byte[]{1, 0, 0});
        setEncryptFlag(EncryptFlag.NO);
        setEncryptKey(0);
    }

    @Override
    public byte[] getMsgBodyByteArr() {
        byte[] verifyCodeBytes = CommonUtils.int2bytes(this.verifyCode);
        return CommonUtils.append(new byte[]{this.resul}, verifyCodeBytes);
    }
}
