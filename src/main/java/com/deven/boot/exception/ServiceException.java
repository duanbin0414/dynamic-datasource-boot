package com.deven.boot.exception;


import com.deven.boot.datasource.constant.ResultCode;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录 @see WebMvcConfigurer
 */
public class ServiceException extends RuntimeException {

    private ResultCode resultCode;

    private ErrContext errContext;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public ServiceException(ErrContext errContext) {
        super(errContext.getErr().getMessage());
        this.errContext = errContext;
    }

    public ErrContext getErrContext() {
        return errContext;
    }

    public void setErrContext(ErrContext errContext) {
        this.errContext = errContext;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
