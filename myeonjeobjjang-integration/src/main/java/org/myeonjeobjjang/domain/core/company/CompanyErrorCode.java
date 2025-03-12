package org.myeonjeobjjang.domain.core.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.myeonjeobjjang.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CompanyErrorCode implements BaseErrorCode {
    COMPANY_NOT_FOUND("DOM_COMPANY_001","해당 기업이 없습니다.", HttpStatus.UNAUTHORIZED),
    DUPLICATED_COMPANY_NAME("DOM_COMPANY_002","이미 사용중인 기업명입니다.", HttpStatus.IM_USED),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}