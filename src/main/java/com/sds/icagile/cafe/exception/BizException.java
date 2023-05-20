package com.sds.icagile.cafe.exception;

public class BizException extends RuntimeException {
    private String message;

    public BizException(String message) {
        super(message);
    }
}
