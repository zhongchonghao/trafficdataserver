package com.kaiyun.io.common.datasource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @program: trafficdataserver
 * @description:
 * @author: Chonghao Zhong
 * @create: 2020-03-25 11:04
 **/
public class SqlSessionFactory {
    private static Logger logger = LoggerFactory.getLogger(SqlSessionFactory.class);

    private static final String CONFIG_FILE = "mybatis-config.xml";
    private static org.apache.ibatis.session.SqlSessionFactory factory;

    static {
        // 读取配置
        InputStream stream = null;
        try {
            stream = Resources.getResourceAsStream(CONFIG_FILE);
        } catch (IOException e) {
            logger.error("获取mybatis配置流对象失败，{}", e.getMessage());
        }
        // 可以根据配置的相应环境读取相应的数据库环境
        // 获取sqlSessionFactory对象
        // SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(stream, "development");
        factory = new SqlSessionFactoryBuilder().build(stream);
    }

    /**
     * 创建连接
     */
    public static SqlSession getSession() {
        return factory.openSession();
    }

    /**
     * 关闭链接
     */
    public static void closeSession(SqlSession session) {
        session.close();
    }
}
