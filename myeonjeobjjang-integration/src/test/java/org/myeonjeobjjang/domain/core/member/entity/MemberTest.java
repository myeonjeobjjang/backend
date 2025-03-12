package org.myeonjeobjjang.domain.core.member.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.myeonjeobjjang.MyeonjeobjjangIntegrationTestSupport;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Member 엔티티를 ")
class MemberTest extends MyeonjeobjjangIntegrationTestSupport {
    @DisplayName("생성할 수 있다.")
    @Test
    void builder() {
        // given
        String userName = "망고";
        String email = "mango@email.com";

        // when
        Member member = Member.builder()
            .userName(userName)
            .email(email)
            .build();

        // then
        assertThat(member)
            .extracting("userName", "email")
            .containsExactlyInAnyOrder(
                userName, email
            );
    }
}