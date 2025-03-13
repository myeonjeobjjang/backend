package org.myeonjeobjjang.domain.core.resume.service;

import org.myeonjeobjjang.domain.core.resume.service.dto.IntegrationResumeRequest.IntegrationResumeCreateRequest;
import org.myeonjeobjjang.domain.core.resume.service.dto.IntegrationResumeRequest.IntegrationResumeInfoRequest;
import org.myeonjeobjjang.domain.core.resume.service.dto.IntegrationResumeResponse.IntegrationResumeInfoResponse;

public interface ResumeService {
    IntegrationResumeInfoResponse create(IntegrationResumeCreateRequest request);
    IntegrationResumeInfoResponse update(IntegrationResumeInfoRequest request);
    IntegrationResumeInfoResponse get(Long resumeId);
}
