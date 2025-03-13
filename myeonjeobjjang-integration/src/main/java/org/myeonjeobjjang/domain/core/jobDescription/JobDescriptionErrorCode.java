package org.myeonjeobjjang.domain.core.jobDescription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.myeonjeobjjang.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JobDescriptionErrorCode implements BaseErrorCode {
    DUPLICATE_JOB_DESCRIPTION("DOM_JOB_DESCRIPTION_001","동일한 직무가 존재합니다.", HttpStatus.IM_USED),
    JOB_DESCRIPTION_NOT_FOUND("DOM_JOB_POSTING_002","해당 직무는 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}