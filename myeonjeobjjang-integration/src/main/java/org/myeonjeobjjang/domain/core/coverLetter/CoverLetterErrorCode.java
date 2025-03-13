package org.myeonjeobjjang.domain.core.coverLetter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.myeonjeobjjang.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CoverLetterErrorCode implements BaseErrorCode {
    COVER_LETTER_NOT_FOUND("DOM_COVER_LETTER_001","해당 자기소개서가 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATED_COVER_LETTER("DOM_COVER_LETTER_002","이미 사용중인 자기소개서입니다.", HttpStatus.IM_USED),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}