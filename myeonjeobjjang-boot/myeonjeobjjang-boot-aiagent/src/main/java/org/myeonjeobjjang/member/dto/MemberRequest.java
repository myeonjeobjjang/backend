package org.myeonjeobjjang.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class MemberRequest {
    public record LoginRequest(
        @Email String email
    ) {
    }

    public record SignUpRequest(
        @Email String email,
        @NotEmpty String userName
    ) {
    }
}
