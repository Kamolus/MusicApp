package com.springmusicapp.core.exception;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
    private final String errorCode;

    public BusinessLogicException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
