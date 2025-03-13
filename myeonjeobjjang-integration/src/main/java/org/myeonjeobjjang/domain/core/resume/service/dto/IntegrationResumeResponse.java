package org.myeonjeobjjang.domain.core.resume.service.dto;

import org.myeonjeobjjang.domain.core.resume.entity.Resume;

public class IntegrationResumeResponse {
    public record IntegrationResumeInfoResponse(
        Long resumeId,
        Long memberId,
        String introduce,
        String major,
        String grade,
        String skill,
        String companyExperience,
        String activity,
        String certificate,
        String price
    ) {
        public static IntegrationResumeInfoResponse toDto(Resume resume) {
            return new IntegrationResumeInfoResponse(
                resume.getResumeId(),
                resume.getMember().getMemberId(),
                resume.getIntroduce(),
                resume.getMajor(),
                resume.getGrade(),
                resume.getSkill(),
                resume.getCompanyExperience(),
                resume.getActivity(),
                resume.getCertificate(),
                resume.getPrice()
            );
        }
    }
}
