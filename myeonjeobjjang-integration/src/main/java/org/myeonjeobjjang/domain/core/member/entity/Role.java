package org.myeonjeobjjang.domain.core.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    APPLICANT("ROLE_APPLICANT"),
    COMPANY("ROLE_COMPANY"),
    ADMIN("ROLE_ADMIN"),
    ;
    private final String roleName;
}
