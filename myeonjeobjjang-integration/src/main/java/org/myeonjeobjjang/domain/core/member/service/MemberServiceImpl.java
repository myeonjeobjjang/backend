package org.myeonjeobjjang.domain.core.member.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.config.security.jwt.JwtTokenProvider;
import org.myeonjeobjjang.config.security.jwt.JwtType;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.domain.core.member.repository.MemberRepository;
import org.myeonjeobjjang.domain.core.member.service.dto.MemberRequest.LoginRequest;
import org.myeonjeobjjang.domain.core.member.service.dto.MemberRequest.SignUpRequest;
import org.myeonjeobjjang.domain.core.member.service.dto.MemberResponse.JwtPairResponse;
import org.myeonjeobjjang.domain.core.member.service.dto.MemberResponse.LoginOrSignUpResponse;
import org.myeonjeobjjang.domain.core.member.service.dto.MemberResponse.MemberInfoResponse;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.myeonjeobjjang.domain.core.member.MemberErrorCode.DUPLICATED_EMAIL;
import static org.myeonjeobjjang.domain.core.member.MemberErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginOrSignUpResponse login(LoginRequest request) {
        Member member = memberRepository.findMemberByEmail(request.email())
            .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));
        return getLoginOrSignUpResponse(member);
    }

    @Transactional
    public LoginOrSignUpResponse signUp(SignUpRequest request) {
        if (memberRepository.findMemberByEmail(request.email()).isPresent()) {
            throw new BaseException(DUPLICATED_EMAIL);
        }
        Member newMember = request.toEntity();
        Member createdMember = memberRepository.save(newMember);
        return getLoginOrSignUpResponse(createdMember);
    }

    private LoginOrSignUpResponse getLoginOrSignUpResponse(Member createdMember) {
        long currentTimeMillis = System.currentTimeMillis();
        JwtPairResponse jwtPairResponse = JwtPairResponse.toDto(
            jwtTokenProvider.createJwt(Long.toString(createdMember.getMemberId()), JwtType.ACCESS_TOKEN, currentTimeMillis),
            jwtTokenProvider.createJwt(Long.toString(createdMember.getMemberId()), JwtType.REFRESH_TOKEN, currentTimeMillis)
        );
        return LoginOrSignUpResponse.toDto(jwtPairResponse, MemberInfoResponse.toDto(createdMember));
    }
}
