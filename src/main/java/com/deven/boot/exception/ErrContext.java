package com.deven.boot.exception;

public class ErrContext {

    private int statusCode;
    private Err err;

    public ErrContext(int statusCode, Err err) {
        this.statusCode = statusCode;
        this.err = err;
    }

    public ErrContext(int statusCode, String code , String message) {
        this.statusCode = statusCode;
        this.err = new Err(code,message);
    }


    public int getStatusCode() {
        return statusCode;
    }

    public Err getErr() {
        return err;
    }

    public class Err {

        private String code;
        private String message;


        public Err(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

    }

}
