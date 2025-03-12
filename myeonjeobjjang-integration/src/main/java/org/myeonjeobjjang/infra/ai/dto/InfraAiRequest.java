package org.myeonjeobjjang.infra.ai.dto;

import lombok.Builder;

public class InfraAiRequest {
    @Builder
    public record EmbeddingPdfRequest(
        String fileName,
        String userName
    ) {
    }

    @Builder
    public record TechRecruitmentRequest(
        String companyName,
        String fileName,
        String userName
    ) {
    }
}
