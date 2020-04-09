package com.kaiyun.io.thread;

import com.kaiyun.io.bean.dto.TrafficVehicleLocationDTO;
import com.kaiyun.io.bean.po.TrafficVehicleLocationPO;
import com.kaiyun.io.common.util.VehicleLocationUtils;
import com.kaiyun.io.mapper.TrafficVehicleLocationMapper;
import com.kaiyun.io.mapper.impl.TrafficVehicleLocationMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: trafficdataserver
 * @description:
 * @author: Chonghao Zhong
 * @create: 2020-03-30 20:43
 **/
public class VehicleLocationThread implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(VehicleLocationThread.class);

    private TrafficVehicleLocationDTO trafficVehicleLocationDTO;

    public VehicleLocationThread(TrafficVehicleLocationDTO trafficVehicleLocationDTO) {
        this.trafficVehicleLocationDTO = trafficVehicleLocationDTO;
    }

    @Override
    public void run() {
        logger.info("车辆定位信息持久化线程启动，内容为：{}", trafficVehicleLocationDTO);

        // 1 根据车牌号查询GPS编号。
        TrafficVehicleLocationMapper mapper = new TrafficVehicleLocationMapperImpl();
        String vehicleNo = trafficVehicleLocationDTO.getVehicleNo();
        String imei = mapper.selectGPSNumByVehicleNo(vehicleNo);

        // 1.1 未查询到GPS编号，写入车辆信息。
        if (null == imei || imei.isEmpty()) {
            imei = VehicleLocationUtils.getImei();
            mapper.insertVehicleInfo(VehicleLocationUtils.getUid(), vehicleNo, imei);
        }

        // 获取PO对象
        TrafficVehicleLocationPO trafficVehicleLocationPO = new TrafficVehicleLocationPO(trafficVehicleLocationDTO);
        trafficVehicleLocationPO.setImei(imei);
        // 2 轨迹表每次都写入
        mapper.insertTrack(trafficVehicleLocationPO);

        // 3 查询定位表，判断该车是否是第一次推送
        imei = mapper.selectPosition(imei);

        // 3.1 未查询到走写入，查询到走更新。
        if (null == imei || imei.isEmpty()) {
            mapper.insertPosition(trafficVehicleLocationPO);
        } else {
            mapper.updatePosition(trafficVehicleLocationPO);
        }

        logger.info("车辆定位信息持久化线程执行结束。");
    }
}
