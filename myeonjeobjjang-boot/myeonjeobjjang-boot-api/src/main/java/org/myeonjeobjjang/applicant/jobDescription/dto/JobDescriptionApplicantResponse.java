package org.myeonjeobjjang.applicant.jobDescription.dto;

import org.myeonjeobjjang.domain.core.jobDescription.service.dto.JobDescriptionResponse;

import java.util.List;

public class JobDescriptionApplicantResponse {
    public record JobDescriptionInfoApplicantResponse(
        List<JobDescriptionResponse.JobDescriptionInfoResponse> jobDescriptionInfos
    ) {
        public static JobDescriptionInfoApplicantResponse toDto(List<JobDescriptionResponse.JobDescriptionInfoResponse> jobDescriptionInfos) {
            return new JobDescriptionInfoApplicantResponse(jobDescriptionInfos);
        }
    }
}
