package com.kaiyun.io.bean.po;

import com.kaiyun.io.bean.dto.TrafficVehicleLocationDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @program: trafficdataserver
 * @description:
 * @author: Chonghao Zhong
 * @create: 2020-03-30 11:25
 **/
@Getter
@Setter
@ToString
public class TrafficVehicleLocationPO {
    /**
     * IMEI（GPS设备编号）
     */
    private String imei;
    /**
     * 经度，单位为1*10^-6度。 4字节
     */
    private Double latitude;
    /**
     * 纬度，单位为1*10^-6度。 4字节
     */
    private Double longitude;
    /**
     * 2字节；海拔高度，单位为米（m）。
     */
    private short altitude;
    /**
     * 2字节； 速度，指卫星定位车载终端设备上传的行车速度信息，为必填项。单位为千米每小时（km/h）。
     */
    private short speed;
    /**
     * 2字节；方向，0-359，单位为度（。），正北为0，顺时针
     */
    private short direction;
    /**
     * 时间
     */
    private Date time;
    /**
     * 车辆颜色 1字节
     */
    private byte vehicleColor;
    /**
     * 2字节；行驶记录速度，指车辆行驶记录设备上传的行车速度信息，为必填项。单位为千米每小时（km/h）。
     */
    private short vehicleSpeed;
    /**
     * 4字节；车辆当前总里程数，值车辆上传的行车里程数。单位单位为千米（km）。
     */
    private int mileage;
    /**
     * 4字节；车辆状态，二进制表示
     */
    private int vehicleStatus;
    /**
     * 4字节；报警状态，二进制表示
     */
    private int alarm;

    public TrafficVehicleLocationPO(TrafficVehicleLocationDTO dto) {
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.altitude = dto.getAltitude();
        this.speed = dto.getSpeed();
        this.direction = dto.getDirection();
        this.time = dto.getTime();
        this.vehicleColor = dto.getVehicleColor();
        this.vehicleSpeed = dto.getVehicleSpeed();
        this.mileage = dto.getMileage();
        this.vehicleStatus = dto.getVehicleStatus();
        this.alarm = dto.getAlarm();
    }

    public TrafficVehicleLocationPO() {
    }
}
