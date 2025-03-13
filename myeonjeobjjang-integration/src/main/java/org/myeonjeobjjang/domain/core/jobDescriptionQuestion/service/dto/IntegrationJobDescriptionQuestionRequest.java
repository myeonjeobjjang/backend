package org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto;

public class IntegrationJobDescriptionQuestionRequest {
    public record IntegrationJobDescriptionQuestionCreateRequest(
        Long questionNumber,
        String question,
        Long jobDescriptionId
    ) {}
}
