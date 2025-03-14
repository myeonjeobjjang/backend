package org.myeonjeobjjang.domain.core.jobDescription.service.dto;

import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;

public class JobDescriptionResponse {
    public record JobDescriptionInfoResponse(
        Long jobDescriptionId,
        String jobName,
        String description,
        Long jobPostingId
    ) {
        public static JobDescriptionInfoResponse toDto(JobDescription jobDescription) {
            return new JobDescriptionInfoResponse(
                jobDescription.getJobDescriptionId(),
                jobDescription.getJobName(),
                jobDescription.getDescription(),
                jobDescription.getJobPosting().getJobPostingId()
            );
        }
    }
}
