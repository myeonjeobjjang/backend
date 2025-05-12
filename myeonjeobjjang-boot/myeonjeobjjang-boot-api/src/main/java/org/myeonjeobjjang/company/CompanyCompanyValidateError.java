package org.myeonjeobjjang.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.myeonjeobjjang.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CompanyCompanyValidateError implements BaseErrorCode {
    INDUSTRY_MUST_POSITIVE("API_COMPANY_001","유효한 산업군이 아닙니다.", HttpStatus.BAD_REQUEST),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
