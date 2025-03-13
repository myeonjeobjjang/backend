package org.myeonjeobjjang.domain.core.resume.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.resume.entity.Resume;
import org.myeonjeobjjang.domain.core.resume.repository.ResumeRepository;
import org.myeonjeobjjang.domain.core.resume.service.dto.IntegrationResumeRequest.IntegrationResumeCreateRequest;
import org.myeonjeobjjang.domain.core.resume.service.dto.IntegrationResumeRequest.IntegrationResumeInfoRequest;
import org.myeonjeobjjang.domain.core.resume.service.dto.IntegrationResumeResponse.IntegrationResumeInfoResponse;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.myeonjeobjjang.common.errorCode.MemberErrorCode.UNAUTHORIZED_ACTION;
import static org.myeonjeobjjang.domain.core.resume.ResumeErrorCode.RESUME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;

    @Override
    public IntegrationResumeInfoResponse create(IntegrationResumeCreateRequest request) {
        Resume newResume = request.toEntity();
        Resume savedResume = resumeRepository.save(newResume);
        return IntegrationResumeInfoResponse.toDto(savedResume);
    }

    @Override
    @Transactional
    public IntegrationResumeInfoResponse update(IntegrationResumeInfoRequest request) {
        Resume resume = resumeRepository.findById(request.resumeId())
            .orElseThrow(() -> new BaseException(RESUME_NOT_FOUND));
        if (!Objects.equals(resume.getMember().getMemberId(), request.member().getMemberId()))
            throw new BaseException(UNAUTHORIZED_ACTION);
        resume.update(request);
        return IntegrationResumeInfoResponse.toDto(resume);
    }

    @Override
    public IntegrationResumeInfoResponse get(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
            .orElseThrow(() -> new BaseException(RESUME_NOT_FOUND));
        return IntegrationResumeInfoResponse.toDto(resume);
    }
}
