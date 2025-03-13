package org.myeonjeobjjang.domain.core.coverLetterItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.myeonjeobjjang.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CoverLetterItemErrorCode implements BaseErrorCode {
    COVER_LETTER_ITEM_NOT_FOUND("DOM_COVER_LETTER_ITEM_001","해당 자기소개서 항목이 없습니다.", HttpStatus.NOT_FOUND),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}