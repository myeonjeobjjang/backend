package org.myeonjeobjjang.domain.core.jobPosting.service.dto;

import java.time.LocalDateTime;

public class IntegrationJobPostingRequest {
    public record IntegrationJobPostingCreateRequest(
        String jobPostingName,
        String jobPostingDescription,
        Long companyId,
        LocalDateTime dueDate
    ) {}
}
