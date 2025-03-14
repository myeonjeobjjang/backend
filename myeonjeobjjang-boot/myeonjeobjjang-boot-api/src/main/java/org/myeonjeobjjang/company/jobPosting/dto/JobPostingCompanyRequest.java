package org.myeonjeobjjang.company.jobPosting.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class JobPostingCompanyRequest {
    public record JobPostingCreateCompanyRequest(
        @NotEmpty
        String jobPostingName,
        @NotEmpty
        String jobPostingDescription,
        @Positive
        Long companyId,
        @FutureOrPresent
        LocalDateTime dueDate
    ) {}
}
