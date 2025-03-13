package org.myeonjeobjjang.applicant.jobPosting;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.jobPosting.service.JobPostingService;
import org.myeonjeobjjang.domain.core.jobPosting.service.dto.IntegrationJobPostingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/applicant/jobPostings")
@RequiredArgsConstructor
public class JobPostingApplicantController {
    private final JobPostingService jobPostingService;

    @GetMapping("/{jobPostingId}")
    public ResponseEntity<IntegrationJobPostingResponse.IntegrationJobPostingInfoResponse> getJobPosting(@PathVariable Long jobPostingId) {
        return ResponseEntity.ok(jobPostingService.get(jobPostingId));
    }

    public record JobPostingApplicantByCompanyResponse(
        List<IntegrationJobPostingResponse.IntegrationJobPostingInfoResponse> jobPostings
    ) {}

    @GetMapping("/companies/{companyId}")
    public ResponseEntity<JobPostingApplicantByCompanyResponse> getJobPostingByCompanyId(@PathVariable Long companyId) {
        return ResponseEntity.ok(new JobPostingApplicantByCompanyResponse(jobPostingService.findByCompanyId(companyId, LocalDateTime.now())));
    }
}
