package org.myeonjeobjjang.domain.core.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    MEMBER("ROLE_MEMBER"),
    COMPANY("ROLE_COMPANY"),
    ADMIN("ROLE_ADMIN"),
    ;
    private final String roleName;
}
