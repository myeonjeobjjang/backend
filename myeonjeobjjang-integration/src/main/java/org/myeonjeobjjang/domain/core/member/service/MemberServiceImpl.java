package org.myeonjeobjjang.domain.core.member.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.common.security.jwt.JwtTokenProvider;
import org.myeonjeobjjang.common.security.jwt.JwtType;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.domain.core.member.repository.MemberRepository;
import org.myeonjeobjjang.domain.core.member.service.dto.IntegrationMemberRequest;
import org.myeonjeobjjang.domain.core.member.service.dto.IntegrationMemberResponse;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.myeonjeobjjang.common.errorCode.MemberErrorCode.DUPLICATED_EMAIL;
import static org.myeonjeobjjang.common.errorCode.MemberErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public IntegrationMemberResponse.LoginOrSignUpResponse login(IntegrationMemberRequest.IntegrationLoginRequest request) {
        Member member = memberRepository.findMemberByEmail(request.email())
            .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));
        return getLoginOrSignUpResponse(member);
    }

    @Transactional
    public IntegrationMemberResponse.LoginOrSignUpResponse signUp(IntegrationMemberRequest.IntegrationSignUpRequest request) {
        if (memberRepository.findMemberByEmail(request.email()).isPresent()) {
            throw new BaseException(DUPLICATED_EMAIL);
        }
        Member newMember = Member.builder()
            .email(request.email())
            .userName(request.userName())
            .build();
        Member createdMember = memberRepository.save(newMember);
        return getLoginOrSignUpResponse(createdMember);
    }

    private IntegrationMemberResponse.LoginOrSignUpResponse getLoginOrSignUpResponse(Member createdMember) {
        long currentTimeMillis = System.currentTimeMillis();
        IntegrationMemberResponse.JwtPairResponse jwtPairResponse = new IntegrationMemberResponse.JwtPairResponse(
            jwtTokenProvider.createJwt(Long.toString(createdMember.getMemberId()), JwtType.ACCESS_TOKEN, currentTimeMillis),
            jwtTokenProvider.createJwt(Long.toString(createdMember.getMemberId()), JwtType.REFRESH_TOKEN, currentTimeMillis)
        );
        IntegrationMemberResponse.MemberInfoResponse memberInfoResponse = new IntegrationMemberResponse.MemberInfoResponse(
            createdMember.getMemberId(),
            createdMember.getEmail(),
            createdMember.getUserName(),
            createdMember.getRole().getRoleName()
        );
        return new IntegrationMemberResponse.LoginOrSignUpResponse(jwtPairResponse, memberInfoResponse);
    }
}
