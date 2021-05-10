package com.deven.boot.datasource.transaction;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * <p>Class: MultiConnectionContextHolder</p>
 * <p>Description:  跨库事务线程标记、当前所有资源的持有者</p>
 *
 * @author Deven
 * @version 1.0
 * @date 2021-03-31
 */
public class MultiConnectionContextHolder {

    private static final InheritableThreadLocal<Set<ConnectWarp>> connectionThreadLocal = new InheritableThreadLocal<>();

    /**
     * 是否开启跨库事务 状态标记
     */
    private static final InheritableThreadLocal<Boolean> mutilTransactionStatus = new InheritableThreadLocal<>();

    /**
     * 清除当前线程持有链接
     */
    public static void clearConnections() {
        connectionThreadLocal.remove();
    }

    /**
     * 获取当前线程持有链接
     */
    public static Set<ConnectWarp> getCurrentConections() {
        if (CollectionUtils.isEmpty(connectionThreadLocal.get())) {
            return Collections.EMPTY_SET;
        }
        return connectionThreadLocal.get();
    }

    /**
     * 新增当前线程持有链接
     */
    public static void addConnection(ConnectWarp connection) {
        Set<ConnectWarp> connectWarps = connectionThreadLocal.get();
        if (Objects.isNull(connectWarps)) {
            connectWarps = Sets.newHashSet();
        }
        connectWarps.add(connection);
        connectionThreadLocal.set(connectWarps);
    }

    /**
     * @Description 查询当前线程是否开启了跨库事务
     * @return boolean
     */
     public static boolean getMutilTransactionStatus() {
         if (Objects.isNull(mutilTransactionStatus.get())) {
             return false;
         }
         return mutilTransactionStatus.get();
     }

    /**
     * @Description 设置当前线程跨库事务状态
     * @return boolean
     */
    public static void setMutilTransactionStatus(boolean status) {
        mutilTransactionStatus.set(status);
    }


    /**
     * 清除当前线程跨库事务状态
     */
    public static void clearMultiTransactionStatus() {
        mutilTransactionStatus.remove();
    }

}
