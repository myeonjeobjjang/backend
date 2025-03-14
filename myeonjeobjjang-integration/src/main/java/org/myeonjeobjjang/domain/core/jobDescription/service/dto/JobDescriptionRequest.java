package org.myeonjeobjjang.domain.core.jobDescription.service.dto;

import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.jobPosting.entity.JobPosting;

public class JobDescriptionRequest {
    public record JobDescriptionCreateRequest(
        String jobName,
        String description,
        Long jobPostingId
    ) {
        public JobDescription toEntity(JobPosting jobPosting) {
            return JobDescription.builder()
                .jobName(jobName)
                .description(description)
                .jobPosting(jobPosting)
                .build();
        }
    }
}
