package com.ut.market.service.exception;

/**
 * Exception used for all bad request http errors
 * */
public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }
}
