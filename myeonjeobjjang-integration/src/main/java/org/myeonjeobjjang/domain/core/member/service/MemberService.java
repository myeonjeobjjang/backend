package org.myeonjeobjjang.domain.core.member.service;

import org.myeonjeobjjang.domain.core.member.service.dto.IntegrationMemberRequest;
import org.myeonjeobjjang.domain.core.member.service.dto.IntegrationMemberResponse;

public interface MemberService {
    IntegrationMemberResponse.LoginOrSignUpResponse login(IntegrationMemberRequest.IntegrationLoginRequest request);
    IntegrationMemberResponse.LoginOrSignUpResponse signUp(IntegrationMemberRequest.IntegrationSignUpRequest request);
}
