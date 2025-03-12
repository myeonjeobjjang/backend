package org.myeonjeobjjang.domain.core.member.service.dto;

public class IntegrationMemberResponse {
    public record JwtPairResponse(String accessToken, String refreshToken) {}
    public record MemberInfoResponse(Long memberId, String email, String userName, String role) {}
    public record LoginOrSignUpResponse(JwtPairResponse jwtPairResponse, MemberInfoResponse memberInfoResponse) {}
}
