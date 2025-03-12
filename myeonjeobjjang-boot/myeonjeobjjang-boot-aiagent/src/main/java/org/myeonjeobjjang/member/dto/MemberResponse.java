package org.myeonjeobjjang.member.dto;

import org.myeonjeobjjang.domain.core.member.entity.Role;

public class MemberResponse {
    public record MemberInfo(
        Long memberId,
        String userName,
        String email,
        Role role
    ) {}
}
