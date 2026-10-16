package com.workintech.zoo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ZooGlobalExceptionHandler {

    @ExceptionHandler(ZooException.class)
    public ResponseEntity<ZooErrorResponse> handleException(ZooException exception) {
        log.error("ZooException akışı tetiklendi: {}", exception.getMessage());
        ZooErrorResponse errorResponse = new ZooErrorResponse(
                exception.getHttpStatus().value(), // Önce status (int)
                exception.getMessage(),            // Sonra message (String)
                System.currentTimeMillis()         // Timestamp (long)
        );
        return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ZooErrorResponse> handleAllExceptions(Exception exception) {
        log.error("Beklenmeyen bir hata oluştu: {}", exception.getMessage());
        ZooErrorResponse errorResponse = new ZooErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // Önce status (int)
                exception.getMessage(),                   // Sonra message (String)
                System.currentTimeMillis()                // Timestamp (long)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}