package org.myeonjeobjjang.domain.core.jobDescription.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.jobDescription.repository.JobDescriptionRepository;
import org.myeonjeobjjang.domain.core.jobDescription.service.dto.JobDescriptionRequest;
import org.myeonjeobjjang.domain.core.jobDescription.service.dto.JobDescriptionRequest.JobDescriptionCreateRequest;
import org.myeonjeobjjang.domain.core.jobDescription.service.dto.JobDescriptionResponse;
import org.myeonjeobjjang.domain.core.jobDescription.service.dto.JobDescriptionResponse.JobDescriptionInfoResponse;
import org.myeonjeobjjang.domain.core.jobPosting.entity.JobPosting;
import org.myeonjeobjjang.domain.core.jobPosting.service.JobPostingService;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.myeonjeobjjang.domain.core.jobDescription.JobDescriptionErrorCode.DUPLICATE_JOB_DESCRIPTION;
import static org.myeonjeobjjang.domain.core.jobDescription.JobDescriptionErrorCode.JOB_DESCRIPTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class JobDescriptionServiceImpl implements JobDescriptionService {
    private final JobDescriptionRepository jobDescriptionRepository;

    private final JobPostingService jobPostingService;

    @Override
    public JobDescriptionInfoResponse create(JobDescriptionCreateRequest request) {
        JobPosting jobPosting = jobPostingService.findById(request.jobPostingId());
        if(jobDescriptionRepository.findJobDescriptionByJobPostingAndJobName(jobPosting, request.jobName()).isPresent())
            throw new BaseException(DUPLICATE_JOB_DESCRIPTION);
        JobDescription newJobDescription = request.toEntity(jobPosting);
        JobDescription savedJobDescription = jobDescriptionRepository.save(newJobDescription);
        return JobDescriptionInfoResponse.toDto(savedJobDescription);
    }

    @Override
    public List<JobDescriptionInfoResponse> getJobDescriptionByJobPosting(Long jobPostingId) {
        JobPosting jobPosting = jobPostingService.findById(jobPostingId);
        List<JobDescription> jobDescriptions = jobDescriptionRepository
            .findJobDescriptionsByJobPosting(jobPosting);
        return jobDescriptions.stream().map(JobDescriptionInfoResponse::toDto).toList();
    }

    @Override
    public JobDescription findById(Long jobDescriptionId) {
        return jobDescriptionRepository.findById(jobDescriptionId)
            .orElseThrow(() -> new BaseException(JOB_DESCRIPTION_NOT_FOUND));
    }
}
