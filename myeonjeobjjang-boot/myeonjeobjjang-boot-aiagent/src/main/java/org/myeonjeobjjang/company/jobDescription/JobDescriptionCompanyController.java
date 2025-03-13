package org.myeonjeobjjang.company.jobDescription;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.jobDescription.service.JobDescriptionService;
import org.myeonjeobjjang.domain.core.jobDescription.service.dto.IntegrationJobDescriptionRequest;
import org.myeonjeobjjang.domain.core.jobDescription.service.dto.IntegrationJobDescriptionResponse;
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

    public record JobDescriptionCreateRequest(
        @NotEmpty
        String jobName,
        @NotEmpty
        String description,
        @Positive
        Long jobPostingId
    ) {}

    @PostMapping
    public ResponseEntity<IntegrationJobDescriptionResponse.IntegrationJobDescriptionInfoResponse> create(@RequestBody @Validated JobDescriptionCreateRequest request) {
        return ResponseEntity.ok(jobDescriptionService.create(new IntegrationJobDescriptionRequest.IntegrationJobDescriptionCreateRequest(request.jobName(), request.description(), request.jobPostingId())));
    }
}
