package org.myeonjeobjjang.applicant.jobPosting.dto;

import org.myeonjeobjjang.domain.core.jobPosting.service.dto.JobPostingResponse;

import java.util.List;

public class JobPostingApplicantReponse {
    public record JobPostingApplicantByCompanyApplicantResponse(
        List<JobPostingResponse.JobPostingInfoResponse> jobPostings
    ) {
        public static JobPostingApplicantByCompanyApplicantResponse toDto(List<JobPostingResponse.JobPostingInfoResponse> jobPostings) {
            return new JobPostingApplicantByCompanyApplicantResponse(jobPostings);
        }
    }
}
