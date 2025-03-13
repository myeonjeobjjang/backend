package org.myeonjeobjjang.domain.core.resume;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.myeonjeobjjang.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResumeErrorCode implements BaseErrorCode {
    RESUME_NOT_FOUND("DOM_RESUME_001","해당 이력서는 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}