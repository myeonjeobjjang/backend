package org.myeonjeobjjang.domain.core.jobDescriptionQuestion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.myeonjeobjjang.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JobDescriptionQeustionErrorCode implements BaseErrorCode {
    DUPLICATE_JOB_DESCRIPTION_QUESTION("DOM_JOB_DESCRIPTION_QUESTION_001","해당 채용직무의 질문번호가 이미 존재합니다.", HttpStatus.IM_USED),
    JOB_DESCRIPTION_QUESTION_NOT_FOUND("DOM_JOB_DESCRIPTION_QUESTION_002","해당 채용직무질문은 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}