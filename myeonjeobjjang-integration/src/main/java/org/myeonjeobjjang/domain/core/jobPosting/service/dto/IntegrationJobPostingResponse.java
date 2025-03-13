package org.myeonjeobjjang.domain.core.jobPosting.service.dto;

import java.time.LocalDateTime;

public class IntegrationJobPostingResponse {
    public record IntegrationJobPostingInfoResponse(
        Long jobPostingId,
        String jobPostingName,
        String jobPostingDescription,
        Long companyId,
        LocalDateTime dueDate,
        LocalDateTime createdAt
    ) {}
}
