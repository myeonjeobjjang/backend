package org.myeonjeobjjang.domain.core.member.repository;

import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByMemberId(Long memberId);
    Optional<Member> findMemberByEmail(String email);
}
