package org.myeonjeobjjang.domain.core.resume.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myeonjeobjjang.domain.common.BaseEntity;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.domain.core.resume.service.dto.ResumeRequest.ResumeInfoRequest;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resume extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @Column(columnDefinition = "TEXT")
    private String introduce;
    private String major;
    private String grade;
    @Column(columnDefinition = "TEXT")
    private String skill;
    @Column(columnDefinition = "TEXT")
    private String companyExperience;
    @Column(columnDefinition = "TEXT")
    private String activity;
    @Column(columnDefinition = "TEXT")
    private String certificate;
    @Column(columnDefinition = "TEXT")
    private String price;

    @Builder
    private Resume(String price, String certificate, String activity, String companyExperience, String skill, String grade, String major, String introduce, Member member) {
        this.price = price;
        this.certificate = certificate;
        this.activity = activity;
        this.companyExperience = companyExperience;
        this.skill = skill;
        this.grade = grade;
        this.major = major;
        this.introduce = introduce;
        this.member = member;
    }

    public void update(ResumeInfoRequest request) {
        if (request.price() != null && !request.price().isEmpty())
            this.price = request.price();
        if (request.certificate() != null && !request.certificate().isEmpty())
            this.certificate = request.certificate();
        if (request.activity() != null && !request.activity().isEmpty())
            this.activity = request.activity();
        if (request.companyExperience() != null && !request.companyExperience().isEmpty())
            this.companyExperience = request.companyExperience();
        if (request.skill() != null && !request.skill().isEmpty())
            this.skill = request.skill();
        if (request.grade() != null && !request.grade().isEmpty())
            this.grade = request.grade();
        if (request.major() != null && !request.major().isEmpty())
            this.major = request.major();
        if (request.introduce() != null && !request.introduce().isEmpty())
            this.introduce = request.introduce();
    }
}
