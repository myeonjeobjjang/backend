package org.myeonjeobjjang.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.myeonjeobjjang.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SecurityErrorCode implements BaseErrorCode {
    EXPIRED_TOKEN("COM_SECURITY_001","토큰이 만료되었습니다.",HttpStatus.UNAUTHORIZED),
    UNSUPPORTED_TOKEN_TYPE("COM_SECURITY_002", "지원하는 토큰 타입이 아닙니다.",HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    INVALID_JWT("COM_SECURITY_003","유효한 JWT가 아닙니다.",HttpStatus.BAD_REQUEST),
    UNSUPPORTED_JWT("COM_SECURITY_004","지원하는 JWT가 아닙니다.",HttpStatus.BAD_REQUEST),

    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
