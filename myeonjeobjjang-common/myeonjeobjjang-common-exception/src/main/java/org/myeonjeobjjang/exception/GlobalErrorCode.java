package org.myeonjeobjjang.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode {
    NOT_VALID_ARGUMENT_ERROR("GLOBAL_001", "올바른 argument를 입력해주세요.", HttpStatus.BAD_REQUEST),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
