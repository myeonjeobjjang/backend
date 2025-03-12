package org.myeonjeobjjang.advice;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponse {
    private final String errorCode;
    private final String message;
    private final LocalDateTime timeStamp;
    public ExceptionResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }
}
