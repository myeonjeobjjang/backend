package org.myeonjeobjjang.domain.core.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    MEMBER("member"),
    COMPANY("compnay"),
    ADMIN("admin"),
    ;
    private final String roleName;
}
