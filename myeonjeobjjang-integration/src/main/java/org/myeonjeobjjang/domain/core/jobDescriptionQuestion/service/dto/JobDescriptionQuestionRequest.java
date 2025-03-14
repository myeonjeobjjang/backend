package org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto;

import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.entity.JobDescriptionQuestion;

public class JobDescriptionQuestionRequest {
    public record JobDescriptionQuestionCreateRequest(
        Long questionNumber,
        String question,
        Long jobDescriptionId
    ) {
        public JobDescriptionQuestion toEntity(JobDescription jobDescription) {
            return JobDescriptionQuestion.builder()
                .questionNumber(questionNumber)
                .question(question)
                .jobDescription(jobDescription)
                .build();
        }
    }
}
