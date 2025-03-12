package org.myeonjeobjjang.config.security;

import lombok.Getter;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

@Getter
public class PrincipalDetails implements UserDetails {
    private Member member;
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PrincipalDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(member.getRole().getRoleName()));
    }

    @Override
    public String getPassword() {
        return passwordEncoder.encode(member.getEmail());
    }

    @Override
    public String getUsername() {
        return member.getUserName();
    }
}
