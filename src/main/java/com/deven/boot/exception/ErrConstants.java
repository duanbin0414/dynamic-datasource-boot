package com.deven.boot.exception;

import org.springframework.http.HttpStatus;


/**
 * 错误常量类
 */
public class ErrConstants {

    public static final ErrContext TRANSACTION_EXCEPTION = new ErrContext(HttpStatus.INTERNAL_SERVER_ERROR.value(), "dynamicDataSource.501001", "multi transaction exception.");
}
