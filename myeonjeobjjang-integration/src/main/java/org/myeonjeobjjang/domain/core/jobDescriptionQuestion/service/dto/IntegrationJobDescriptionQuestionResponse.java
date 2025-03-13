package org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto;

public class IntegrationJobDescriptionQuestionResponse {
    public record IntegrationJobDescriptionQuestionInfoResponse(
        Long questionNumber,
        Long jobDescriptionQuestionId,
        String question,
        Long jobDescriptionId
    ) {}
}
