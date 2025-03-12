package org.myeonjeobjjang.infra.ai.dto;

import lombok.Builder;

public class InfraAiResponse {
    @Builder
    public record TechRecruitmentResponse(
        String technicalCapabilityKeywords,
        String projectExperiences,
        String specialty,
        String mattersRequiringVerificationByTheApplicant,
        String reasonsForTheApplicantsInadequacy
    ) {
    }
}
