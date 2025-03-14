package org.myeonjeobjjang.domain.core.jobPosting.service.dto;

import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.jobPosting.entity.JobPosting;

import java.time.LocalDateTime;

public class JobPostingRequest {
    public record JobPostingCreateRequest(
        String jobPostingName,
        String jobPostingDescription,
        Long companyId,
        LocalDateTime dueDate
    ) {
        public JobPosting toEntity(Company company) {
            return JobPosting.builder()
                .jobPostingName(jobPostingName)
                .jobPostingDescription(jobPostingDescription)
                .company(company)
                .duedate(dueDate)
                .build();
        }
    }
}
