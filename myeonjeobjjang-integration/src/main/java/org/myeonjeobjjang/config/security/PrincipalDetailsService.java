package org.myeonjeobjjang.config.security;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.member.repository.MemberRepository;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static org.myeonjeobjjang.domain.core.member.MemberErrorCode.MEMBER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return new PrincipalDetails(memberRepository.findMemberByMemberId(Long.parseLong(userId))
            .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND)));
    }
}
