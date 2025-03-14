package org.myeonjeobjjang.applicant.member.dto;

import org.myeonjeobjjang.domain.core.member.entity.Role;

public class MemberApplicantResponse {
    public record MemberInfoApplicantResponse(
        Long memberId,
        String userName,
        String email,
        Role role
    ) {}
}
