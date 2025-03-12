package org.myeonjeobjjang.domain.core.member.repository;

import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
