package org.myeonjeobjjang.company.jobPosting;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.company.jobPosting.dto.JobPostingCompanyRequest.JobPostingCreateCompanyRequest;
import org.myeonjeobjjang.domain.core.jobPosting.service.JobPostingService;
import org.myeonjeobjjang.domain.core.jobPosting.service.dto.JobPostingRequest.JobPostingCreateRequest;
import org.myeonjeobjjang.domain.core.jobPosting.service.dto.JobPostingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/company/jobPostings")
@RequiredArgsConstructor
public class JobPostingCompanyController {
    private final JobPostingService jobPostingService;

    @PostMapping
    public ResponseEntity<JobPostingResponse.JobPostingInfoResponse> create(
        @RequestBody @Validated JobPostingCreateCompanyRequest request
    ) {
        return ResponseEntity.ok(jobPostingService.create(
            new JobPostingCreateRequest(
                request.jobPostingName(),
                request.jobPostingDescription(),
                request.companyId(),
                request.dueDate()
            ), LocalDateTime.now()
        ));
    }
}
