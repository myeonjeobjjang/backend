package org.myeonjeobjjang.applicant.jobDescriptionQuestion.dto;

import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto.JobDescriptionQuestionResponse;

import java.util.List;

public class JobDescriptionQuestionApplicantResponse {
    public record JobDescriptionQuestionInfoApplicantResponse(
        List<JobDescriptionQuestionResponse.JobDescriptionQuestionInfoResponse> jobDescriptionQuestionInfos
    ) {
        public static JobDescriptionQuestionInfoApplicantResponse toDto(List<JobDescriptionQuestionResponse.JobDescriptionQuestionInfoResponse> jobDescriptionQuestionInfos) {
            return new JobDescriptionQuestionInfoApplicantResponse(jobDescriptionQuestionInfos);
        }
    }
}
