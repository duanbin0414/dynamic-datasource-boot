package com.deven.boot.datasource.service;

import com.alibaba.fastjson.JSON;
import com.deven.boot.datasource.MyHikariConfig;
import com.deven.boot.datasource.constant.ResultCode;
import com.deven.boot.datasource.util.RedisUtil;
import com.deven.boot.datasource.vo.AddNodeRespVo;
import com.deven.boot.datasource.vo.CorpDataSourceNode;
import com.deven.boot.datasource.vo.CorpDataSourceNodes;
import com.deven.boot.datasource.vo.DataSourceAuthInfo;
import com.deven.boot.exception.ServiceException;
import com.google.common.collect.Maps;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;

@Service
public class DynamicDataSourceService {
    /**
     * 数据库实例配置信息在redis中的key
     */
    private String nodeKey = "db_node";

    /**
     * 企业数据库名和数据库实例地址之间映射在redis中的key
     */
    private String nodeMapKey = "db_node_map";

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceService.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MyHikariConfig myHikariConfig;

    /**
     * 数据库实例和数据源的映射
     */
    private Map<String, DataSource> resolvedDataSources = Maps.newHashMap();

    /**
     * 查询所有的企业数据库实例节点配置信息
     *
     * @return
     */
    public CorpDataSourceNodes getDbNodes() {

        String nodeJson = redisUtil.getString(nodeKey);
        CorpDataSourceNodes corpDataSourceNodes = JSON.parseObject(nodeJson, CorpDataSourceNodes.class);
        return corpDataSourceNodes;

    }

    /**
     * 根据dbName查询dbServer
     *
     * @param dbName
     * @return
     */
    public String getDbServerByDbName(String dbName) {
        return redisUtil.hashGet(nodeMapKey, dbName);
    }

    /**
     * 增加dbName和dbServer映射关系(新企业开通的时候需要调用此方法)
     *
     * @param dbName
     * @param dbServer
     */
    public void addDbName2DbServerReleation(String dbName, String dbServer) {
        redisUtil.hashSet(nodeMapKey, dbName, dbServer);
    }

    /**
     * 根据配置创建dataSource, 如果该节点设置了自己的最大连接数, 则覆盖默认的最大连接数
     *
     * @param node
     * @return
     */
    private DataSource buildDataSourceByConfig(CorpDataSourceNode node) {

        if (node == null) {
            return null;
        }
        DataSourceAuthInfo config = node.getConfig();
        String url = config.getUrl();
        String password = config.getPassword();
        String username = config.getUsername();
        Integer maxPoolSize = config.getMaxPoolSize();

        HikariConfig hikariConfig = new HikariConfig();
        BeanUtils.copyProperties(myHikariConfig, hikariConfig);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        if (maxPoolSize != null) {
            hikariConfig.setMaximumPoolSize(maxPoolSize);
        }

        String poolName = "HikariCP_" + node.getNode();
        hikariConfig.setPoolName(poolName);

        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        logger.info("{} dataSource is {}, maxPoolSize={}", poolName, hikariDataSource, hikariDataSource.getMaximumPoolSize());

        return hikariDataSource;
    }

    /**
     * 增加数据源
     *
     * @param node
     */
    public void createDataSource(CorpDataSourceNode node) {
        DataSource dataSource = buildDataSourceByConfig(node);
        if (dataSource != null) {
            this.resolvedDataSources.put(node.getNode(), dataSource);
        }
    }

    public AddNodeRespVo addDataSourceNode(CorpDataSourceNode node) {

        if (node == null) {
            throw new ServiceException(ResultCode.INVALID_PARAM, "node can't be empty");
        }
        CorpDataSourceNodes currentDbNodes = getDbNodes();
        if (!currentDbNodes.containsNode(node)) {
            currentDbNodes.addNode(node);
            //创建数据源
            createDataSource(node);
            //更新redis
            String nodeJson = JSON.toJSONString(currentDbNodes);
            redisUtil.setString(nodeKey, nodeJson);
            return new AddNodeRespVo("true");
        } else {
            logger.warn("node={} already exists!", node);
            throw new ServiceException(ResultCode.RESOURCE_ALEADY_EXITS, "node already exists");
        }

    }

    public Map<String, DataSource> getResolvedDataSources() {
        return resolvedDataSources;
    }

}
