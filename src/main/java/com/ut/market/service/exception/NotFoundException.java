package com.ut.market.service.exception;

/**
 * Exception used for all not found http errors
 * */
public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
