package org.myeonjeobjjang.domain.core.member.service.dto;

import org.myeonjeobjjang.domain.core.member.entity.Member;

public class MemberRequest {
    public record LoginRequest(String email) {
    }

    public record SignUpRequest(String email, String userName) {
        public Member toEntity() {
            return Member.builder()
                .email(email)
                .userName(userName)
                .build();
        }
    }
}
