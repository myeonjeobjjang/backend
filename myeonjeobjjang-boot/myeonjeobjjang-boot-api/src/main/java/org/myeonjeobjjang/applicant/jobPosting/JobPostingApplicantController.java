package org.myeonjeobjjang.applicant.jobPosting;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.applicant.jobPosting.dto.JobPostingApplicantReponse.JobPostingApplicantByCompanyApplicantResponse;
import org.myeonjeobjjang.domain.core.jobPosting.service.JobPostingService;
import org.myeonjeobjjang.domain.core.jobPosting.service.dto.JobPostingResponse.JobPostingInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/applicant/jobPostings")
@RequiredArgsConstructor
public class JobPostingApplicantController {
    private final JobPostingService jobPostingService;

    @GetMapping("/{jobPostingId}")
    public ResponseEntity<JobPostingInfoResponse> getJobPosting(@PathVariable Long jobPostingId) {
        return ResponseEntity.ok(jobPostingService.get(jobPostingId));
    }

    @GetMapping("/companies/{companyId}")
    public ResponseEntity<JobPostingApplicantByCompanyApplicantResponse> getJobPostingByCompanyId(
        @PathVariable Long companyId
    ) {
        return ResponseEntity.ok(JobPostingApplicantByCompanyApplicantResponse.toDto(
            jobPostingService.findByCompanyId(companyId, LocalDateTime.now())
        ));
    }
}
