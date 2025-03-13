package org.myeonjeobjjang.domain.core.jobDescription.service.dto;

public class IntegrationJobDescriptionRequest {
    public record IntegrationJobDescriptionCreateRequest(
        String jobName,
        String description,
        Long jobPostingId
    ) {}
}
