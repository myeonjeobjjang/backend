package org.myeonjeobjjang.domain.core.member.service.dto;

public class IntegrationMemberRequest {
    public record IntegrationLoginRequest(String email) {}
    public record IntegrationSignUpRequest(String email, String userName) {}
}
