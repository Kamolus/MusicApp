package com.springmusicapp.exception;


import org.springframework.http.HttpStatus;

public class MusicianException extends RuntimeException {

    private final HttpStatus status;

    public MusicianException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}