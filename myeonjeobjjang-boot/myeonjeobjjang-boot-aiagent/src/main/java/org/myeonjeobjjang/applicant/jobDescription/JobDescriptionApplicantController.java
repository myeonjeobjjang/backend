package org.myeonjeobjjang.applicant.jobDescription;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.jobDescription.service.JobDescriptionService;
import org.myeonjeobjjang.domain.core.jobDescription.service.dto.IntegrationJobDescriptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/applicant/jobDescriptions")
@RequiredArgsConstructor
public class JobDescriptionApplicantController {
    private final JobDescriptionService jobDescriptionService;

    public record JobDescriptionInfoResponse (
        List<IntegrationJobDescriptionResponse.IntegrationJobDescriptionInfoResponse> jobDescriptionInfos
    ) {}

    @GetMapping("/jobPostings/{jobPostingId}")
    public ResponseEntity<JobDescriptionInfoResponse> get(@PathVariable Long jobPostingId) {
        return ResponseEntity.ok(new JobDescriptionInfoResponse(jobDescriptionService.getJobDescriptionByJobPosting(jobPostingId)));
    }
}
