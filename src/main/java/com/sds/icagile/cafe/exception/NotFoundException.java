package com.sds.icagile.cafe.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
    private HttpStatus status = HttpStatus.NOT_FOUND;
    private String message;

    public NotFoundException(String message) {
        super(message);
    }
}
