package org.myeonjeobjjang.domain.core.jobDescription.service;

import org.myeonjeobjjang.domain.core.jobDescription.service.dto.IntegrationJobDescriptionRequest;
import org.myeonjeobjjang.domain.core.jobDescription.service.dto.IntegrationJobDescriptionResponse;

import java.util.List;

public interface JobDescriptionService {
    IntegrationJobDescriptionResponse.IntegrationJobDescriptionInfoResponse create(IntegrationJobDescriptionRequest.IntegrationJobDescriptionCreateRequest request);
    List<IntegrationJobDescriptionResponse.IntegrationJobDescriptionInfoResponse> getJobDescriptionByJobPosting(Long jobPostingId);
}
