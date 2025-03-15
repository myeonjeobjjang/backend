package org.myeonjeobjjang.domain.core.resume.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.resume.entity.Resume;
import org.myeonjeobjjang.domain.core.resume.repository.ResumeRepository;
import org.myeonjeobjjang.domain.core.resume.service.dto.ResumeRequest.ResumeCreateRequest;
import org.myeonjeobjjang.domain.core.resume.service.dto.ResumeRequest.ResumeInfoRequest;
import org.myeonjeobjjang.domain.core.resume.service.dto.ResumeResponse.ResumeInfoResponse;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.myeonjeobjjang.domain.core.member.MemberErrorCode.UNAUTHORIZED_ACTION;
import static org.myeonjeobjjang.domain.core.resume.ResumeErrorCode.RESUME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;

    @Override
    public ResumeInfoResponse create(ResumeCreateRequest request) {
        Resume newResume = request.toEntity();
        Resume savedResume = resumeRepository.save(newResume);
        return ResumeInfoResponse.toDto(savedResume);
    }

    @Override
    @Transactional
    public ResumeInfoResponse update(ResumeInfoRequest request) {
        Resume resume = resumeRepository.findById(request.resumeId())
            .orElseThrow(() -> new BaseException(RESUME_NOT_FOUND));
        if (!Objects.equals(resume.getMember().getMemberId(), request.member().getMemberId()))
            throw new BaseException(UNAUTHORIZED_ACTION);
        resume.update(request);
        return ResumeInfoResponse.toDto(resume);
    }

    @Override
    public ResumeInfoResponse get(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
            .orElseThrow(() -> new BaseException(RESUME_NOT_FOUND));
        return ResumeInfoResponse.toDto(resume);
    }

    @Override
    public Resume findById(Long resumeId) {
        return resumeRepository.findById(resumeId)
            .orElseThrow(() -> new BaseException(RESUME_NOT_FOUND));
    }
}
