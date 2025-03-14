package org.myeonjeobjjang.applicant.jobDescription;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.applicant.jobDescription.dto.JobDescriptionApplicantResponse.JobDescriptionInfoApplicantResponse;
import org.myeonjeobjjang.domain.core.jobDescription.service.JobDescriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applicant/jobDescriptions")
@RequiredArgsConstructor
public class JobDescriptionApplicantController {
    private final JobDescriptionService jobDescriptionService;

    @GetMapping("/jobPostings/{jobPostingId}")
    public ResponseEntity<JobDescriptionInfoApplicantResponse> get(@PathVariable Long jobPostingId) {
        return ResponseEntity.ok(JobDescriptionInfoApplicantResponse.toDto(
            jobDescriptionService.getJobDescriptionByJobPosting(jobPostingId)
        ));
    }
}
