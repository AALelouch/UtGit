package com.ut.market.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception advice used to handle all bad request exceptions
 * */
@RestControllerAdvice
public class BadRequestExceptionAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> notFoundException(BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(e.getMessage(), 400));
    }

}
