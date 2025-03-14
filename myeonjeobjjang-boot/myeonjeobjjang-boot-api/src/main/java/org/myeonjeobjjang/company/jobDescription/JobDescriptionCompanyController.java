package org.myeonjeobjjang.company.jobDescription;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.company.jobDescription.dto.JobDescriptionCompanyRequest.JobDescriptionCreateCompanyRequest;
import org.myeonjeobjjang.domain.core.jobDescription.service.JobDescriptionService;
import org.myeonjeobjjang.domain.core.jobDescription.service.dto.JobDescriptionRequest.JobDescriptionCreateRequest;
import org.myeonjeobjjang.domain.core.jobDescription.service.dto.JobDescriptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company/jobDescriptions")
@RequiredArgsConstructor
public class JobDescriptionCompanyController {
    private final JobDescriptionService jobDescriptionService;

    @PostMapping
    public ResponseEntity<JobDescriptionResponse.JobDescriptionInfoResponse> create(
        @RequestBody @Validated JobDescriptionCreateCompanyRequest request
    ) {
        return ResponseEntity.ok(jobDescriptionService.create(
            new JobDescriptionCreateRequest(request.jobName(), request.description(), request.jobPostingId())
        ));
    }
}
