package org.myeonjeobjjang.domain.core.jobPosting.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.company.service.CompanyService;
import org.myeonjeobjjang.domain.core.jobPosting.entity.JobPosting;
import org.myeonjeobjjang.domain.core.jobPosting.repository.JobPostingRepository;
import org.myeonjeobjjang.domain.core.jobPosting.service.dto.JobPostingRequest.JobPostingCreateRequest;
import org.myeonjeobjjang.domain.core.jobPosting.service.dto.JobPostingResponse.JobPostingInfoResponse;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.myeonjeobjjang.domain.core.jobPosting.JobPostingErrorCode.*;

@Service
@RequiredArgsConstructor
public class JobPostingServiceImpl implements JobPostingService {
    private final JobPostingRepository jobPostingRepository;

    private final CompanyService companyService;

    @Override
    public JobPostingInfoResponse create(JobPostingCreateRequest request, LocalDateTime currentDateTime) {
        if (request.dueDate().isBefore(currentDateTime)) {
            throw new BaseException(DUEDATE_IS_PASSED);
        }
        Company company = companyService.findById(request.companyId());
        if (jobPostingRepository.findDuplicateJobPostingByCompanyAndJobPostingName(company, request.jobPostingName()).isPresent())
            throw new BaseException(DUPLICATE_JOB_POSTING);
        JobPosting newJobPosting = request.toEntity(company);
        JobPosting savedJobPosting = jobPostingRepository.save(newJobPosting);
        return JobPostingInfoResponse.toDto(savedJobPosting);
    }

    @Override
    public JobPostingInfoResponse get(Long jobPostingId) {
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
            .orElseThrow(() -> new BaseException(JOB_POSTING_NOT_FOUND));
        return JobPostingInfoResponse.toDto(jobPosting);
    }

    @Override
    public List<JobPostingInfoResponse> findByCompanyId(Long companyId, LocalDateTime currentDateTime) {
        Company company = companyService.findById(companyId);
        List<JobPosting> liveJobPosting = jobPostingRepository.findJobPostingByCompanyAndDuedateAfter(company, currentDateTime);
        return liveJobPosting.stream().map(JobPostingInfoResponse::toDto).toList();
    }

    @Override
    public JobPosting findById(Long jobPostingId) {
        return jobPostingRepository.findById(jobPostingId)
            .orElseThrow(() -> new BaseException(JOB_POSTING_NOT_FOUND));
    }
}
