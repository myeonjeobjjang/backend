package org.myeonjeobjjang.domain.core.industry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.myeonjeobjjang.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum IndustryErrorCode implements BaseErrorCode {
    INDUSTRY_NOT_FOUND("DOM_INDUSTRY_001","해당 산업군이 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATED_INDUSTRY_NAME("DOM_INDUSTRY_002","이미 사용중인 산업군명입니다.", HttpStatus.IM_USED),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}