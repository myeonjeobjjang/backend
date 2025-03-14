package org.myeonjeobjjang.company.jobDescription.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public class JobDescriptionCompanyRequest {
    public record JobDescriptionCreateCompanyRequest(
        @NotEmpty
        String jobName,
        @NotEmpty
        String description,
        @Positive
        Long jobPostingId
    ) {}
}
