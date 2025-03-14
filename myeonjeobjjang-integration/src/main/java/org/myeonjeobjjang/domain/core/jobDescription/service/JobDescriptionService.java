package org.myeonjeobjjang.domain.core.jobDescription.service;

import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.jobDescription.service.dto.JobDescriptionRequest.JobDescriptionCreateRequest;
import org.myeonjeobjjang.domain.core.jobDescription.service.dto.JobDescriptionResponse.JobDescriptionInfoResponse;

import java.util.List;

public interface JobDescriptionService {
    JobDescriptionInfoResponse create(JobDescriptionCreateRequest request);

    List<JobDescriptionInfoResponse> getJobDescriptionByJobPosting(Long jobPostingId);

    JobDescription findById(Long jobDescriptionId);
}
