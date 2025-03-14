package org.myeonjeobjjang.domain.core.resume.service.dto;

import org.myeonjeobjjang.domain.core.resume.entity.Resume;

public class ResumeResponse {
    public record ResumeInfoResponse(
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
        public static ResumeInfoResponse toDto(Resume resume) {
            return new ResumeInfoResponse(
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
