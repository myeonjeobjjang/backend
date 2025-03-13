package org.myeonjeobjjang.domain.core.jobPosting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.myeonjeobjjang.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JobPostingErrorCode implements BaseErrorCode {
    DUEDATE_IS_PASSED("DOM_JOB_POSTING_001","제출기한이 과거입니다.", HttpStatus.TOO_EARLY),
    DUPLICATE_JOB_POSTING("DOM_JOB_POSTING_002","동일한 공고가 존재합니다.", HttpStatus.IM_USED),
    JOB_POSTING_NOT_FOUND("DOM_JOB_POSTING_003","해당 공고는 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}