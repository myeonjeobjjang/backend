package org.myeonjeobjjang.applicant.resume.dto;

import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.domain.core.resume.service.dto.ResumeRequest.ResumeCreateRequest;
import org.myeonjeobjjang.domain.core.resume.service.dto.ResumeRequest.ResumeInfoRequest;

public class ResumeApplicationApplicantRequest {
    public record ResumeCreateApplicantRequest(
        String introduce,
        String major,
        String grade,
        String skill,
        String companyExperience,
        String activity,
        String certificate,
        String price
    ) {
        public ResumeCreateRequest toServiceRequest(Member member) {
            return new ResumeCreateRequest(
                member,
                introduce,
                major,
                grade,
                skill,
                companyExperience,
                activity,
                certificate,
                price
            );
        }
    }

    public record ResumeInfoApplicantRequest(
        String introduce,
        String major,
        String grade,
        String skill,
        String companyExperience,
        String activity,
        String certificate,
        String price
    ) {
        public ResumeInfoRequest toServiceRequest(Long resumeId, Member member) {
            return new ResumeInfoRequest(
                resumeId,
                member,
                introduce,
                major,
                grade,
                skill,
                companyExperience,
                activity,
                certificate,
                price
            );
        }
    }
}
