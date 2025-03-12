package org.myeonjeobjjang.domain.core.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    MEMBER("사용자"),
    ;
    private final String roleName;
}
