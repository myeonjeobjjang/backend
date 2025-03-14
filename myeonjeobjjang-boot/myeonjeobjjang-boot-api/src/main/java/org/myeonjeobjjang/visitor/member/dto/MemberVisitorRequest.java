package org.myeonjeobjjang.visitor.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class MemberVisitorRequest {
    public record LoginVisitorRequest(
        @Email String email
    ) {
    }

    public record SignUpVisitorRequest(
        @Email String email,
        @NotEmpty String userName
    ) {
    }
}
