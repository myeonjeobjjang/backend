package org.myeonjeobjjang.company.jobDescriptionQuestion.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public class JobDescriptionQuestionCompanyRequest {
    public record JobDescriptionQuestionCreateCompanyRequest(
        @Positive
        Long questionNumber,
        @NotEmpty
        String question,
        @Positive
        Long jobDescriptionId
    ) {}
}
