package org.myeonjeobjjang.domain.core.jobPosting.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.company.service.CompanyService;
import org.myeonjeobjjang.domain.core.jobPosting.entity.JobPosting;
import org.myeonjeobjjang.domain.core.jobPosting.repository.JobPostingRepository;
import org.myeonjeobjjang.domain.core.jobPosting.service.dto.IntegrationJobPostingRequest;
import org.myeonjeobjjang.domain.core.jobPosting.service.dto.IntegrationJobPostingResponse;
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
    public IntegrationJobPostingResponse.IntegrationJobPostingInfoResponse create(IntegrationJobPostingRequest.IntegrationJobPostingCreateRequest request, LocalDateTime currentDateTime) {
        if (request.dueDate().isBefore(currentDateTime)) {
            throw new BaseException(DUEDATE_IS_PASSED);
        }
        Company company = companyService.findById(request.companyId());
        if(jobPostingRepository.findDuplicateJobPostingByCompanyAndJobPostingName(company, request.jobPostingName()).isPresent())
            throw new BaseException(DUPLICATE_JOB_POSTING);
        JobPosting newJobPosting = JobPosting.builder()
            .jobPostingName(request.jobPostingName())
            .jobPostingDescription(request.jobPostingDescription())
            .company(company)
            .duedate(request.dueDate())
            .build();
        JobPosting savedJobPosting = jobPostingRepository.save(newJobPosting);
        return new IntegrationJobPostingResponse.IntegrationJobPostingInfoResponse(
            savedJobPosting.getJobPostingId(),
            savedJobPosting.getJobPostingName(),
            savedJobPosting.getJobPostingDescription(),
            savedJobPosting.getCompany().getCompanyId(),
            savedJobPosting.getDuedate(),
            savedJobPosting.getCreatedAt()
        );
    }

    @Override
    public IntegrationJobPostingResponse.IntegrationJobPostingInfoResponse get(Long jobPostingId) {
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
            .orElseThrow(() -> new BaseException(JOB_POSTING_NOT_FOUND));
        return new IntegrationJobPostingResponse.IntegrationJobPostingInfoResponse(
            jobPosting.getJobPostingId(),
            jobPosting.getJobPostingName(),
            jobPosting.getJobPostingDescription(),
            jobPosting.getCompany().getCompanyId(),
            jobPosting.getDuedate(),
            jobPosting.getCreatedAt()
        );
    }

    @Override
    public List<IntegrationJobPostingResponse.IntegrationJobPostingInfoResponse> findByCompanyId(Long companyId, LocalDateTime currentDateTime) {
        Company company = companyService.findById(companyId);
        List<JobPosting> liveJobPosting = jobPostingRepository.findJobPostingByCompanyAndDuedateAfter(company, currentDateTime);
        return liveJobPosting.stream().map(jobPosting ->
            new IntegrationJobPostingResponse.IntegrationJobPostingInfoResponse(
                jobPosting.getJobPostingId(),
                jobPosting.getJobPostingName(),
                jobPosting.getJobPostingDescription(),
                jobPosting.getCompany().getCompanyId(),
                jobPosting.getDuedate(),
                jobPosting.getCreatedAt()
            )).toList();
    }
}
