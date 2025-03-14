package org.myeonjeobjjang.domain.core.jobPosting.service.dto;

import org.myeonjeobjjang.domain.core.jobPosting.entity.JobPosting;

import java.time.LocalDateTime;

public class JobPostingResponse {
    public record JobPostingInfoResponse(
        Long jobPostingId,
        String jobPostingName,
        String jobPostingDescription,
        Long companyId,
        LocalDateTime dueDate,
        LocalDateTime createdAt
    ) {
        public static JobPostingInfoResponse toDto(JobPosting jobPosting) {
            return new JobPostingInfoResponse(
                jobPosting.getJobPostingId(),
                jobPosting.getJobPostingName(),
                jobPosting.getJobPostingDescription(),
                jobPosting.getCompany().getCompanyId(),
                jobPosting.getDuedate(),
                jobPosting.getCreatedAt()
            );
        }
    }
}
