package org.myeonjeobjjang.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus httpStatus;
    public BaseException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode.getMessage());
        this.errorCode = baseErrorCode.getErrorCode();
        this.httpStatus = baseErrorCode.getHttpStatus();
    }
    public BaseException(BaseErrorCode baseErrorCode, Throwable cause) {
        super(baseErrorCode.getMessage(), cause);
        this.errorCode = baseErrorCode.getErrorCode();
        this.httpStatus = baseErrorCode.getHttpStatus();
    }
}
