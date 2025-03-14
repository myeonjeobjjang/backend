package org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto;

import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.entity.JobDescriptionQuestion;

public class JobDescriptionQuestionResponse {
    public record JobDescriptionQuestionInfoResponse(
        Long questionNumber,
        Long jobDescriptionQuestionId,
        String question,
        Long jobDescriptionId
    ) {
        public static JobDescriptionQuestionInfoResponse toDto(JobDescriptionQuestion jobDescriptionQuestion) {
            return new JobDescriptionQuestionInfoResponse(
                jobDescriptionQuestion.getQuestionNumber(),
                jobDescriptionQuestion.getJobDescriptionQuestionId(),
                jobDescriptionQuestion.getQuestion(),
                jobDescriptionQuestion.getJobDescription().getJobDescriptionId()
            );
        }
    }
}
