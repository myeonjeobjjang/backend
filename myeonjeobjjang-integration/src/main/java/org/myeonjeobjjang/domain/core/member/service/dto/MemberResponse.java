package org.myeonjeobjjang.domain.core.member.service.dto;

import org.myeonjeobjjang.domain.core.member.entity.Member;

public class MemberResponse {
    public record JwtPairResponse(String accessToken, String refreshToken) {
        public static JwtPairResponse toDto(String accessToken, String refreshToken) {
            return new JwtPairResponse(accessToken, refreshToken);
        }
    }

    public record MemberInfoResponse(Long memberId, String email, String userName, String role) {
        public static MemberInfoResponse toDto(Member member) {
            return new MemberInfoResponse(
                member.getMemberId(),
                member.getEmail(),
                member.getUserName(),
                member.getRole().getRoleName()
            );
        }
    }

    public record LoginOrSignUpResponse(JwtPairResponse jwtPairResponse, MemberInfoResponse memberInfoResponse) {
        public static LoginOrSignUpResponse toDto(JwtPairResponse jwtPairResponse, MemberInfoResponse memberInfoResponse) {
            return new LoginOrSignUpResponse(jwtPairResponse, memberInfoResponse);
        }
    }
}
