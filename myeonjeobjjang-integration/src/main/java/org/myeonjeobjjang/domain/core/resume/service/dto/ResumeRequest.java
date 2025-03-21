package org.myeonjeobjjang.domain.core.resume.service.dto;

import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.domain.core.resume.entity.Resume;

public class ResumeRequest {
    public record ResumeCreateRequest(
        Member member,
        String introduce,
        String major,
        String grade,
        String skill,
        String companyExperience,
        String activity,
        String certificate,
        String price
    ) {
        public Resume toEntity() {
            return Resume.builder()
                .member(member)
                .introduce(introduce)
                .major(major)
                .grade(grade)
                .skill(skill)
                .companyExperience(companyExperience)
                .activity(activity)
                .certificate(certificate)
                .price(price)
                .build();
        }
    }

    public record ResumeInfoRequest(
        Long resumeId,
        Member member,
        String introduce,
        String major,
        String grade,
        String skill,
        String companyExperience,
        String activity,
        String certificate,
        String price
    ) {
    }
}
