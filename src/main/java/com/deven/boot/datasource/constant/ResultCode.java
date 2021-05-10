package com.deven.boot.datasource.constant;

import org.springframework.http.HttpStatus;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {

    SUCCESS("200", HttpStatus.OK),
    INVALID_PARAM("1001", HttpStatus.BAD_REQUEST),
    RESOURCE_ALEADY_EXITS("1002", HttpStatus.BAD_REQUEST),
    AUTH_FAILD("2001", HttpStatus.UNAUTHORIZED),
    PERMISSION_DENIED("3001", HttpStatus.FORBIDDEN),
    RESOURCE_NOT_EXITS("4001", HttpStatus.NOT_FOUND),
    TASK_OCCUPIED("4061", HttpStatus.NOT_ACCEPTABLE),
    SERVICE_ERROR("5001", HttpStatus.INTERNAL_SERVER_ERROR),
    DB_ERROR("5002", HttpStatus.INTERNAL_SERVER_ERROR);
    private final String code;

    private HttpStatus httpStatus;


    ResultCode(String code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String code() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


    public static ResultCode parseValue(String statusCode) {
        ResultCode[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ResultCode status = var1[var3];
            if (status.code.equals(statusCode)) {
                return status;
            }
        }

        return null;
    }
}
