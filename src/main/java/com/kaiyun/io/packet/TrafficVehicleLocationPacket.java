package com.kaiyun.io.packet;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @program: trafficdataserver
 * @description: 车辆GPS信息包(0x1202)
 * @author: Chonghao Zhong
 * @create: 2020-03-19 16:43
 **/
@Getter
@Setter
@ToString
public class TrafficVehicleLocationPacket extends BasePacket {
    /**
     * 车牌号 21字节
     */
    private String vehicleNo;
    /**
     * 车辆颜色 1字节
     */
    private byte vehicleColor;
    /**
     * 子业务类型标识 2字节
     */
    private short dataType;
    /**
     * 后续数据长度 4字节  后续长度为36，不足补0
     */
    private int dataLength;
    /**
     * 该字段标识传输的定位信息是否使用国家测绘局批准的地图保密插件进行加密。加密标识：1-已加密，0-未加密。1字节
     */
    private byte excrypt;
    /**
     * 日月年（dmyy），年的表示是先将年转换成2为十六进制数，如2009标识为0x070xD9.  4字节
     */
    private LocalDate date;
    /**
     * 时分秒（hms） 3字节
     */
    private LocalTime time;
    /**
     * 经度，单位为1*10^-6度。 4字节
     */
    private int lon;
    /**
     * 纬度，单位为1*10^-6度。 4字节
     */
    private int lat;
    /**
     * 2字节； 速度，指卫星定位车载终端设备上传的行车速度信息，为必填项。单位为千米每小时（km/h）。
     */
    private short vec1;
    /**
     * 2字节；行驶记录速度，指车辆行驶记录设备上传的行车速度信息，为必填项。单位为千米每小时（km/h）。
     */
    private short vec2;
    /**
     * 4字节；车辆当前总里程数，值车辆上传的行车里程数。单位单位为千米（km）。
     */
    private int vec3;
    /**
     * 2字节；方向，0-359，单位为度（。），正北为0，顺时针
     */
    private short direction;
    /**
     * 2字节；海拔高度，单位为米（m）。
     */
    private short altitude;
    /**
     * 4字节；车辆状态，二进制表示
     */
    private int state;
    /**
     * 4字节；报警状态，二进制表示
     */
    private int alarm;

    @Override
    public byte[] getMsgBodyByteArr() {
        return new byte[0];
    }
}
