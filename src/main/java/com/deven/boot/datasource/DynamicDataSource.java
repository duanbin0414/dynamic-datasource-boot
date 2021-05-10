package com.deven.boot.datasource;

import com.deven.boot.datasource.service.DynamicDataSourceService;
import com.deven.boot.datasource.transaction.ConnectWarp;
import com.deven.boot.datasource.transaction.MultiConnectionContextHolder;
import com.deven.boot.datasource.vo.CorpDataSourceNode;
import com.deven.boot.datasource.vo.CorpDataSourceNodes;
import com.ctrip.framework.apollo.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Primary
@Component
public class DynamicDataSource extends AbstractDataSource implements CommandLineRunner {

    private static final String DEFAULT_DB = "coolcollege";

    @Autowired
    private DataSource defaultDataSource;

    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = DataSourceUtils.getConnection(this.determineTargetDataSource());
        return realGetConnection(connection);
    }

    /**
     *  真正获取链接的方法
     *  对获取数据库链接方法包装、保存到线程上下文
     *  @return Connection
     */
    private Connection realGetConnection(Connection connection) throws SQLException {
        connection.setCatalog(getCurrentDbName());
        // 检查是否开启了跨库事务
        boolean mutilTransactionStatus = MultiConnectionContextHolder.getMutilTransactionStatus();
        if (mutilTransactionStatus) {
            // 包装Connection对象，覆写commit方法，使mybatis自动提交失效
            ConnectWarp connectWarp = new ConnectWarp(connection);
            connectWarp.setAutoCommit(false);
            // 保存获取到的数据库链接到当前线程上下文
            MultiConnectionContextHolder.addConnection(connectWarp);
            // 开启跨库事务、返回包装后的数据库链接
            return connectWarp;
        }
        // 返回原生链接、不影响正常数据库自动操作提交
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection connection = this.determineTargetDataSource().getConnection(username,password);
        return realGetConnection(connection);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return iface.isInstance(this) ? (T) this : this.determineTargetDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this) || this.determineTargetDataSource().isWrapperFor(iface);
    }


    protected DataSource determineTargetDataSource() {

        DataSource dataSource = null;
        Map<String, DataSource> resolvedDataSources = dynamicDataSourceService.getResolvedDataSources();
        if (resolvedDataSources != null) {
            String lookupKey = getDbServerByDbName();
            if (!StringUtils.isEmpty(lookupKey)) {
                dataSource = resolvedDataSources.get(lookupKey);
            }
        }
        if (dataSource == null) {
            dataSource = defaultDataSource;
        }
        return dataSource;
    }

    /**
     * 通过dbName获取dbServer
     *
     * @return
     */
    protected String getDbServerByDbName() {
        String dbName = getCurrentDbName();
        return dynamicDataSourceService.getDbServerByDbName(dbName);
    }

    /**
     * 查询当前线程上下文对应的库名
     *
     * @return
     */
    protected String getCurrentDbName() {
        String dbName = DynamicDataSourceContextHolder.getDataSourceType();
        if (StringUtils.isBlank(dbName)) {
            dbName = DEFAULT_DB;
        }
        return dbName;
    }


    @Override
    public void run(String... args) throws Exception {

        CorpDataSourceNodes corpDataSourceNodes = dynamicDataSourceService.getDbNodes();

        List<CorpDataSourceNode> nodes = corpDataSourceNodes.getNodes();

        nodes.forEach(node -> {
            dynamicDataSourceService.createDataSource(node);
        });
    }
}
