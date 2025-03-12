package org.myeonjeobjjang.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    String getMessage();
    String getErrorCode();
    HttpStatus getHttpStatus();
}
