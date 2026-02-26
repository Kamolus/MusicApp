package com.springmusicapp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        ErrorResponse errorResponse = new ErrorResponse(status.value(), "Validation Error: " + errorMessage);

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    @ExceptionHandler(MusicianException.class)
    public ResponseEntity<Object> handleMusicianException(MusicianException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(ex.getStatus().value(), ex.getMessage());

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), ex.getStatus(), request);
    }

    @ExceptionHandler(BandException.class)
    public ResponseEntity<Object> handleBandException(BandException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(ex.getStatus().value(), ex.getMessage());

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), ex.getStatus(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected server error occurred."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
