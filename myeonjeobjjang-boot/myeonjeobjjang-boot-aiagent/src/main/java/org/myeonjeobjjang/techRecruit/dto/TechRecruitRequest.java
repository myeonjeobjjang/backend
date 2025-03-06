package org.myeonjeobjjang.techRecruit.dto;

import jakarta.validation.constraints.NotEmpty;

public class TechRecruitRequest {
    public record EmbeddingRequest(
        @NotEmpty
        String fileName,
        @NotEmpty
        String userName
    ) {
    }

    public record RetreieveRequest(
        @NotEmpty
        String companyName,
        @NotEmpty
        String fileName,
        @NotEmpty
        String userName
    ) {
    }
}
