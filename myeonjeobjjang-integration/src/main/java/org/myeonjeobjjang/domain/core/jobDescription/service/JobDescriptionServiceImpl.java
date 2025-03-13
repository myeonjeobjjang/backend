package org.myeonjeobjjang.domain.core.jobDescription.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.jobDescription.repository.JobDescriptionRepository;
import org.myeonjeobjjang.domain.core.jobDescription.service.dto.IntegrationJobDescriptionRequest;
import org.myeonjeobjjang.domain.core.jobDescription.service.dto.IntegrationJobDescriptionResponse;
import org.myeonjeobjjang.domain.core.jobPosting.entity.JobPosting;
import org.myeonjeobjjang.domain.core.jobPosting.service.JobPostingService;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.myeonjeobjjang.domain.core.jobDescription.JobDescriptionErrorCode.DUPLICATE_JOB_DESCRIPTION;

@Service
@RequiredArgsConstructor
public class JobDescriptionServiceImpl implements JobDescriptionService {
    private final JobDescriptionRepository jobDescriptionRepository;

    private final JobPostingService jobPostingService;

    @Override
    public IntegrationJobDescriptionResponse.IntegrationJobDescriptionInfoResponse create(IntegrationJobDescriptionRequest.IntegrationJobDescriptionCreateRequest request) {
        JobPosting jobPosting = jobPostingService.findById(request.jobPostingId());
        if(jobDescriptionRepository.findJobDescriptionByJobPostingAndJobName(jobPosting, request.jobName()).isPresent())
            throw new BaseException(DUPLICATE_JOB_DESCRIPTION);
        JobDescription newJobDescription = JobDescription.builder()
            .jobName(request.jobName())
            .description(request.description())
            .jobPosting(jobPosting)
            .build();
        JobDescription savedJobDescription = jobDescriptionRepository.save(newJobDescription);
        return new IntegrationJobDescriptionResponse.IntegrationJobDescriptionInfoResponse(savedJobDescription.getJobDescriptionId(), savedJobDescription.getJobName(), savedJobDescription.getDescription(), savedJobDescription.getJobPosting().getJobPostingId());
    }

    @Override
    public List<IntegrationJobDescriptionResponse.IntegrationJobDescriptionInfoResponse> getJobDescriptionByJobPosting(Long jobPostingId) {
        JobPosting jobPosting = jobPostingService.findById(jobPostingId);
        List<JobDescription> jobDescriptions = jobDescriptionRepository.findJobDescriptionsByJobPosting(jobPosting);
        return jobDescriptions.stream().map(jd ->
            new IntegrationJobDescriptionResponse.IntegrationJobDescriptionInfoResponse(jd.getJobDescriptionId(), jd.getJobName(), jd.getDescription(), jd.getJobPosting().getJobPostingId()))
            .toList();
    }
}
