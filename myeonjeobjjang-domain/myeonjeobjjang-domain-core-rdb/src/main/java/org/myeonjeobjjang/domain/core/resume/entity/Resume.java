package org.myeonjeobjjang.domain.core.resume.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myeonjeobjjang.domain.common.BaseEntity;
import org.myeonjeobjjang.domain.core.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resume extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @Column(columnDefinition = "TEXT")
    private String introduce;
    private String major;
    private String grade;
    @Column(columnDefinition = "TEXT")
    private String skill;
    @Column(columnDefinition = "TEXT")
    private String compnayExperience;
    @Column(columnDefinition = "TEXT")
    private String activity;
    @Column(columnDefinition = "TEXT")
    private String certificate;
    @Column(columnDefinition = "TEXT")
    private String price;

    @Builder
    private Resume(String price, String certificate, String activity, String compnayExperience, String skill, String grade, String major, String introduce, Member member) {
        this.price = price;
        this.certificate = certificate;
        this.activity = activity;
        this.compnayExperience = compnayExperience;
        this.skill = skill;
        this.grade = grade;
        this.major = major;
        this.introduce = introduce;
        this.member = member;
    }
}
