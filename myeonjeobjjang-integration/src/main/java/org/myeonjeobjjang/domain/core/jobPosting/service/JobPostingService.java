package org.myeonjeobjjang.domain.core.jobPosting.service;

import org.myeonjeobjjang.domain.core.jobPosting.entity.JobPosting;
import org.myeonjeobjjang.domain.core.jobPosting.service.dto.JobPostingRequest.JobPostingCreateRequest;
import org.myeonjeobjjang.domain.core.jobPosting.service.dto.JobPostingResponse.JobPostingInfoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface JobPostingService {
    JobPostingInfoResponse create(JobPostingCreateRequest request, LocalDateTime currentDateTime);

    JobPostingInfoResponse get(Long jobPostingId);

    List<JobPostingInfoResponse> findByCompanyId(Long companyId, LocalDateTime currentDateTime);

    JobPosting findById(Long jobPostingId);
}
