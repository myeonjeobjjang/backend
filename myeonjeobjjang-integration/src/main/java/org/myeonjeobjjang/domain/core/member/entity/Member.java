package org.myeonjeobjjang.domain.core.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myeonjeobjjang.domain.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String userName;
    @Column(updatable = false)
    private String email;
    private String password;

    @Builder
    private Member(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
