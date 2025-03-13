package org.myeonjeobjjang.company.jobPosting;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.jobPosting.service.JobPostingService;
import org.myeonjeobjjang.domain.core.jobPosting.service.dto.IntegrationJobPostingRequest;
import org.myeonjeobjjang.domain.core.jobPosting.service.dto.IntegrationJobPostingResponse;
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

    public record JobPostingCreateRequest (
        @NotEmpty
        String jobPostingName,
        @NotEmpty
        String jobPostingDescription,
        @Positive
        Long companyId,
        @FutureOrPresent
        LocalDateTime dueDate
    ) {}

    @PostMapping
    public ResponseEntity<IntegrationJobPostingResponse.IntegrationJobPostingInfoResponse> create(@RequestBody @Validated JobPostingCreateRequest request) {
        return ResponseEntity.ok(jobPostingService.create(new IntegrationJobPostingRequest.IntegrationJobPostingCreateRequest(request.jobPostingName(), request.jobPostingDescription(), request.companyId(), request.dueDate()), LocalDateTime.now()));
    }
}
