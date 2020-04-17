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

        // 其他配置根据MyBatis主配置文件进行配置
        dds.setInitialSize(Integer.parseInt(this.props.getProperty("initialSize")));
        dds.setMinIdle(Integer.parseInt(this.props.getProperty("minIdle")));
        dds.setMaxActive(Integer.parseInt(this.props.getProperty("maxActive")));
        dds.setMaxWait(Long.parseLong(this.props.getProperty("maxWait")));
        dds.setTimeBetweenEvictionRunsMillis(Long.parseLong(this.props.getProperty("timeBetweenEvictionRunsMillis")));
        dds.setMinEvictableIdleTimeMillis(Long.parseLong(this.props.getProperty("minEvictableIdleTimeMillis")));
        dds.setValidationQuery(this.props.getProperty("validationQuery"));
        dds.setTestWhileIdle(Boolean.parseBoolean(this.props.getProperty("testWhileIdle")));
        dds.setTestOnBorrow(Boolean.parseBoolean(this.props.getProperty("testOnBorrow")));
        dds.setTestOnReturn(Boolean.parseBoolean(this.props.getProperty("testOnReturn")));
        dds.setPoolPreparedStatements(Boolean.parseBoolean(this.props.getProperty("poolPreparedStatements")));
        dds.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(this.props.getProperty("maxPoolPreparedStatementPerConnectionSize")));
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
