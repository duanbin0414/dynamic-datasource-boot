package com.deven.boot.datasource;

import com.deven.boot.datasource.constant.Constants;
import com.deven.boot.datasource.holder.UserHolder;

public class DataSourceHelper {

    /**
     * 切换到自己的数据库
     */
    public static void changeToMy() {
        DynamicDataSourceContextHolder.clearDataSourceType();
        String dbName = Constants.ENTERPRISE_DATABASE_PREFIX + UserHolder.getUser().getEnterpriseId();
        DynamicDataSourceContextHolder.setDataSourceType(dbName);
    }

    /**
     * 切换到指定的数据库
     * @param datasource
     */
    public static void changeToSpecificDataSource(String datasource) {
        DynamicDataSourceContextHolder.clearDataSourceType();
        String dbName = Constants.ENTERPRISE_DATABASE_PREFIX + datasource;
        DynamicDataSourceContextHolder.setDataSourceType(dbName);
    }

    /**
     * 重置链接到主库
     */
    public static void reset() {
        DynamicDataSourceContextHolder.clearDataSourceType();
    }
}
