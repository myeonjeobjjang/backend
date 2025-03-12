package org.myeonjeobjjang.domain.core.jobPosting.service;

import org.myeonjeobjjang.domain.core.jobPosting.service.dto.IntegrationJobPostingRequest;
import org.myeonjeobjjang.domain.core.jobPosting.service.dto.IntegrationJobPostingResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface JobPostingService {
    IntegrationJobPostingResponse.IntegrationJobPostingInfoResponse create(IntegrationJobPostingRequest.IntegrationJobPostingCreateRequest request, LocalDateTime currentDateTime);
    IntegrationJobPostingResponse.IntegrationJobPostingInfoResponse get(Long jobPostingId);
    List<IntegrationJobPostingResponse.IntegrationJobPostingInfoResponse> findByCompanyId(Long companyId, LocalDateTime currentDateTime);
}
