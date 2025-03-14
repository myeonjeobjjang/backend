package org.myeonjeobjjang.domain.core.member.service;

import org.myeonjeobjjang.domain.core.member.service.dto.MemberRequest.LoginRequest;
import org.myeonjeobjjang.domain.core.member.service.dto.MemberRequest.SignUpRequest;
import org.myeonjeobjjang.domain.core.member.service.dto.MemberResponse.LoginOrSignUpResponse;

public interface MemberService {
    LoginOrSignUpResponse login(LoginRequest request);

    LoginOrSignUpResponse signUp(SignUpRequest request);
}
