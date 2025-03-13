package org.myeonjeobjjang.applicant.resume.dto;

import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.domain.core.resume.service.dto.IntegrationResumeRequest;

public class ResumeApplicationRequest {
    public record ResumeCreateRequest(
        String introduce,
        String major,
        String grade,
        String skill,
        String companyExperience,
        String activity,
        String certificate,
        String price
    ) {
        public IntegrationResumeRequest.IntegrationResumeCreateRequest toIntegrationRequest(Member member) {
            return new IntegrationResumeRequest.IntegrationResumeCreateRequest(
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

    public record ResumeInfoRequest(
        String introduce,
        String major,
        String grade,
        String skill,
        String companyExperience,
        String activity,
        String certificate,
        String price
    ) {
        public IntegrationResumeRequest.IntegrationResumeInfoRequest toIntegrationRequest(Long resumeId, Member member) {
            return new IntegrationResumeRequest.IntegrationResumeInfoRequest(
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
