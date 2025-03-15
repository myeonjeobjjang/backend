package org.myeonjeobjjang.domain.core.conversation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.myeonjeobjjang.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ConversationErrorCode implements BaseErrorCode {
    CONVERSATION_NOT_FOUND("DOM_CONVERSATION_001","해당 대화가 없습니다.", HttpStatus.NOT_FOUND),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}