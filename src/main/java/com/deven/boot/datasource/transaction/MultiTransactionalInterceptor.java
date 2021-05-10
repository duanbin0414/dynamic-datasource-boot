package com.deven.boot.datasource.transaction;

import com.deven.boot.exception.ErrConstants;
import com.deven.boot.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.sql.SQLException;
import java.util.Set;


/**
 * <p>Class: MultiTransaction</p>
 * <p>Description:  MultiTransactional注解aop处理</p>
 *
 * @author Deven
 * @version 1.0
 * @date 2021-03-31
 */
@Slf4j
@Aspect
public class MultiTransactionalInterceptor {

    /**
     *  对添加了@MultiTransactional方法进行线程标记
     */
    @Before(value = "@annotation(com.deven.boot.datasource.transaction.MultiTransactional)")
    public void beform() {
        MultiConnectionContextHolder.setMutilTransactionStatus(true);
    }

    /**
     *  对有@MultiTransactional方法进行线程标记
     *  且抛出异常的线程进行事务回退
     */
    @AfterThrowing(value = "@annotation(com.deven.boot.datasource.transaction.MultiTransactional)")
    public void afterThrowing() {
        // 从当前线程持有的链接、回退事务
        Set<ConnectWarp> currentConnections = MultiConnectionContextHolder.getCurrentConections();
        try {
            for (ConnectWarp connection : currentConnections) {
                connection.rollback();
                connection.realClose();
            }
        } catch (SQLException e) {
            throw new ServiceException(ErrConstants.TRANSACTION_EXCEPTION);
        } finally {
            MultiConnectionContextHolder.clearConnections();
            MultiConnectionContextHolder.clearMultiTransactionStatus();
        }
    }

    /**
     *  对有@MultiTransactional线程标记
     *  且正常执行结束的方法进行事务提交
     *
     */
    @AfterReturning(value = "@annotation(com.deven.boot.datasource.transaction.MultiTransactional)")
    public void after() {
        // 跨库事务方法成功执行、提交事务
        Set<ConnectWarp> currentConnections = MultiConnectionContextHolder.getCurrentConections();
        try {
            for (ConnectWarp connection : currentConnections) {
                connection.realCommit();
                connection.realClose();
            }
        } catch (SQLException e) {
            throw new ServiceException(ErrConstants.TRANSACTION_EXCEPTION);
        } finally {
            MultiConnectionContextHolder.clearConnections();
            MultiConnectionContextHolder.clearMultiTransactionStatus();
        }
    }
}
