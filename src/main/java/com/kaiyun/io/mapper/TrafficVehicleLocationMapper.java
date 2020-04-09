package com.kaiyun.io.mapper;

import com.kaiyun.io.bean.po.TrafficVehicleLocationPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @program: trafficdataserver
 * @description: 车辆位置信息映射
 * @author: Chonghao Zhong
 * @create: 2020-03-25 14:24
 **/
public interface TrafficVehicleLocationMapper {
    /**
     * 根据车辆号牌号码查询GPS设备编号
     *
     * @param vehicleNo 号牌号码
     * @return IMEI（GPS设备编号）
     */
    @Select("SELECT t.imei FROM VEHICLE t where t.numb = #{vehicleNo}")
    String selectGPSNumByVehicleNo(String vehicleNo);

    /**
     * 将车辆信息插入到车辆表中
     *
     * @param code      车辆编码
     * @param vehicleNo 号牌号码
     * @param gpsNum    IMEI（GPS设备编号）
     * @return 插入成功的条数
     */
    @Insert("insert into VEHICLE (CODE, NUMB, IMEI, STATUS, GROUPCODE) VALUES (#{CODE}, #{NUMB}, #{IMEI}, '0', 'jiate')")
    int insertVehicleInfo(@Param("CODE") String code, @Param("NUMB") String vehicleNo, @Param("IMEI") String gpsNum);

    /**
     * 写入车辆定位信息（轨迹表，每次都需写入）
     *
     * @param trafficVehicleLocationPO 写入参数
     * @return 插入成功的条数
     */
    @Insert("insert into VEHICLE_GPS_TRACK" +
            "  (IMEI," +
            "   LATITUDE," +
            "   LONGITUDE," +
            "   ALTITUDE," +
            "   SPEED," +
            "   DIRECTION," +
            "   TIME," +
            "   VEHICLECOLOR," +
            "   VEHICLESPEED," +
            "   MILEAGE," +
            "   VEHICLESTATUS," +
            "   ISALARM)" +
            "VALUES" +
            "  (#{imei}, #{latitude}, #{longitude}, #{altitude}, #{speed}, #{direction}, #{time}, #{vehicleColor}, #{vehicleSpeed}, #{mileage}, #{vehicleStatus}, #{alarm})")
    int insertTrack(TrafficVehicleLocationPO trafficVehicleLocationPO);

    /**
     * 查询车辆定位信息，确认是否是第一次推送（定位表）
     *
     * @param imei IMEI（GPS设备编号）
     * @return 查询结果
     */
    @Select("SELECT t.imei FROM VEHICLE_GPS_POSITION t where t.IMEI = #{imei}")
    String selectPosition(String imei);

    /**
     * 写入车辆定位信息（定位表，第一次推送走写入）
     *
     * @param trafficVehicleLocationPO 写入参数
     * @return 插入成功的条数
     */
    @Insert("insert into VEHICLE_GPS_POSITION" +
            "  (IMEI," +
            "   LATITUDE," +
            "   LONGITUDE," +
            "   ALTITUDE," +
            "   SPEED," +
            "   DIRECTION," +
            "   TIME," +
            "   VEHICLECOLOR," +
            "   VEHICLESPEED," +
            "   MILEAGE," +
            "   VEHICLESTATUS," +
            "   ISALARM)" +
            "VALUES" +
            "  (#{imei}, #{latitude}, #{longitude}, #{altitude}, #{speed}, #{direction}, #{time}, #{vehicleColor}, #{vehicleSpeed}, #{mileage}, #{vehicleStatus}, #{alarm})")
    int insertPosition(TrafficVehicleLocationPO trafficVehicleLocationPO);

    /**
     * 更新车辆定位信息（定位表，非第一次推送走更新，只记录最新的）
     *
     * @param trafficVehicleLocationPO 更新参数
     * @return 更新成功的条数
     */
    @Update("UPDATE VEHICLE_GPS_POSITION" +
            "   SET LATITUDE = #{latitude}," +
            "       LONGITUDE = #{longitude}," +
            "       ALTITUDE = #{altitude}," +
            "       SPEED = #{speed}," +
            "       DIRECTION = #{direction}," +
            "       TIME = #{time}," +
            "       VEHICLECOLOR = #{vehicleColor}," +
            "       VEHICLESPEED = #{vehicleSpeed}," +
            "       MILEAGE = #{mileage}," +
            "       VEHICLESTATUS = #{vehicleStatus}," +
            "       ISALARM = #{alarm}" +
            " WHERE IMEI = #{imei}")
    int updatePosition(TrafficVehicleLocationPO trafficVehicleLocationPO);
}
