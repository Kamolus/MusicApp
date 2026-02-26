package com.springmusicapp.exception;

import org.springframework.http.HttpStatus;

public class BandException extends RuntimeException {

    private final HttpStatus status;

    public BandException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
