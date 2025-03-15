package org.myeonjeobjjang.domain.core.resume.service;

import org.myeonjeobjjang.domain.core.resume.entity.Resume;
import org.myeonjeobjjang.domain.core.resume.service.dto.ResumeRequest.ResumeCreateRequest;
import org.myeonjeobjjang.domain.core.resume.service.dto.ResumeRequest.ResumeInfoRequest;
import org.myeonjeobjjang.domain.core.resume.service.dto.ResumeResponse.ResumeInfoResponse;

public interface ResumeService {
    ResumeInfoResponse create(ResumeCreateRequest request);
    ResumeInfoResponse update(ResumeInfoRequest request);
    ResumeInfoResponse get(Long resumeId);

    Resume findById(Long resumeId);
}
