package org.myeonjeobjjang.common.errorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.myeonjeobjjang.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {
    MEMBER_NOT_FOUND("DOM_MEMBER_001","해당 사용자가 없습니다.", HttpStatus.UNAUTHORIZED),
    DUPLICATED_EMAIL("DOM_MEMBER_002","이미 사용중인 이메일입니다.", HttpStatus.IM_USED),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
