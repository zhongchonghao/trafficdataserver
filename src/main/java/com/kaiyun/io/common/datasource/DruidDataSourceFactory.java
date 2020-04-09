package com.kaiyun.io.common.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @program: trafficdataserver
 * @description: Druid连接池配置
 * @author: Chonghao Zhong
 * @create: 2020-03-25 10:58
 **/
public class DruidDataSourceFactory implements DataSourceFactory {
    private static Logger logger = LoggerFactory.getLogger(DruidDataSourceFactory.class);

    private Properties props;

    @Override
    public DataSource getDataSource() {
        DruidDataSource dds = new DruidDataSource();
        dds.setDriverClassName(this.props.getProperty("driver"));
        dds.setUrl(this.props.getProperty("url"));
        dds.setUsername(this.props.getProperty("username"));
        dds.setPassword(this.props.getProperty("password"));
        // 其他配置可以根据MyBatis主配置文件进行配置
        try {
            dds.init();
        } catch (SQLException e) {
            logger.error("数据源初始化失败，{}", e.getMessage());
        }
        return dds;
    }

    @Override
    public void setProperties(Properties props) {
        this.props = props;
    }
}
