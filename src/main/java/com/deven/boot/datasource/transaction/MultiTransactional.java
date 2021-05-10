/**
 * <p>Title: MultiTransaction.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: www.zto.com</p>
 */
package com.deven.boot.datasource.transaction;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <p>Class: MultiTransaction</p>
 * <p>Description: 支持跨库事务</p>
 *
 * @author Deven
 * @version 1.0
 * @date 2021-03-31
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MultiTransactional {

    @AliasFor("transactionManager")
    String[] value() default {};
}
