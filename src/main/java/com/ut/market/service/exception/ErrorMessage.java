package com.ut.market.service.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO used to contain data related errors and exceptions
 */
@NoArgsConstructor
@Data
public class ErrorMessage {
    private String message;
    private Integer code;

    public ErrorMessage(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}
