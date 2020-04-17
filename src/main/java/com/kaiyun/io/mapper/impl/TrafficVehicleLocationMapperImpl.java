package com.kaiyun.io.mapper.impl;

import com.kaiyun.io.bean.po.TrafficVehicleLocationPO;
import com.kaiyun.io.common.datasource.SqlSessionFactory;
import com.kaiyun.io.mapper.TrafficVehicleLocationMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * @program: trafficdataserver
 * @description:
 * @author: Chonghao Zhong
 * @create: 2020-03-25 14:29
 **/
public class TrafficVehicleLocationMapperImpl implements TrafficVehicleLocationMapper {
    @Override
    public String selectGPSNumByVehicleNo(String vehicleNo) {
        // 获取sqlSession
        SqlSession session = SqlSessionFactory.getSession();
        String imei;

        try {
            // 获取接口的实现类对象
            // 会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            TrafficVehicleLocationMapper mapper = session.getMapper(TrafficVehicleLocationMapper.class);

            //调用代理对象方法
            imei = mapper.selectGPSNumByVehicleNo(vehicleNo);
        } finally {
            // 关闭sqlSession
            SqlSessionFactory.closeSession(session);
        }

        return imei;
    }

    @Override
    public int insertVehicleInfo(String code, String vehicleNo, String gpsNum) {
        // 获取sqlSession
        SqlSession session = SqlSessionFactory.getSession();
        int insert;

        try {
            // 获取接口的实现类对象
            // 会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            TrafficVehicleLocationMapper mapper = session.getMapper(TrafficVehicleLocationMapper.class);

            //调用代理对象方法
            insert = mapper.insertVehicleInfo(code, vehicleNo, gpsNum);

            //如果做了更新、插入或删除操作，必须commit
            session.commit();
        } finally {
            // 关闭sqlSession
            SqlSessionFactory.closeSession(session);
        }

        return insert;
    }

    @Override
    public int insertTrack(TrafficVehicleLocationPO trafficVehicleLocationPO) {
        // 获取sqlSession
        SqlSession session = SqlSessionFactory.getSession();
        int insert;

        try {
            // 获取接口的实现类对象
            // 会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            TrafficVehicleLocationMapper mapper = session.getMapper(TrafficVehicleLocationMapper.class);

            //调用代理对象方法
            insert = mapper.insertTrack(trafficVehicleLocationPO);

            //如果做了更新、插入或删除操作，必须commit
            session.commit();
        } finally {
            // 关闭sqlSession
            SqlSessionFactory.closeSession(session);
        }

        return insert;
    }

    @Override
    public String selectPosition(String imei) {
        // 获取sqlSession
        SqlSession session = SqlSessionFactory.getSession();

        try {
            // 获取接口的实现类对象
            // 会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            TrafficVehicleLocationMapper mapper = session.getMapper(TrafficVehicleLocationMapper.class);

            //调用代理对象方法
            imei = mapper.selectPosition(imei);
        } finally {
            // 关闭sqlSession
            SqlSessionFactory.closeSession(session);
        }

        return imei;
    }

    @Override
    public int insertPosition(TrafficVehicleLocationPO trafficVehicleLocationPO) {
        // 获取sqlSession
        SqlSession session = SqlSessionFactory.getSession();
        int insert;

        try {
            // 获取接口的实现类对象
            // 会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            TrafficVehicleLocationMapper mapper = session.getMapper(TrafficVehicleLocationMapper.class);

            //调用代理对象方法
            insert = mapper.insertPosition(trafficVehicleLocationPO);

            //如果做了更新、插入或删除操作，必须commit
            session.commit();
        } finally {
            // 关闭sqlSession
            SqlSessionFactory.closeSession(session);
        }

        return insert;
    }

    @Override
    public int updatePosition(TrafficVehicleLocationPO trafficVehicleLocationPO) {
        // 获取sqlSession
        SqlSession session = SqlSessionFactory.getSession();
        int update;

        try {
            // 获取接口的实现类对象
            // 会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            TrafficVehicleLocationMapper mapper = session.getMapper(TrafficVehicleLocationMapper.class);

            //调用代理对象方法
            update = mapper.updatePosition(trafficVehicleLocationPO);

            //如果做了更新、插入或删除操作，必须commit
            session.commit();
        } finally {
            // 关闭sqlSession
            SqlSessionFactory.closeSession(session);
        }

        return update;
    }
}
