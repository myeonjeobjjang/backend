package org.myeonjeobjjang.domain.core.jobDescription.service.dto;

public class IntegrationJobDescriptionResponse {
    public record IntegrationJobDescriptionInfoResponse(
        Long jobDescriptionId,
        String jobName,
        String description,
        Long jobPostingId
    ) {}
}
