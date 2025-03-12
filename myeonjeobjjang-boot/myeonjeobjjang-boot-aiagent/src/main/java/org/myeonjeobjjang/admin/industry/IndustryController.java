package org.myeonjeobjjang.admin.industry;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.industry.service.IndustryService;
import org.myeonjeobjjang.domain.core.industry.service.dto.IntegrationIndustryRequest;
import org.myeonjeobjjang.domain.core.industry.service.dto.IntegrationIndustryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/industry")
@RequiredArgsConstructor
public class IndustryController {
    private final IndustryService industryService;

    public record CreateIndustryRequest(
        @NotEmpty
        String industryName,
        @NotEmpty
        String industryInformation
    ) {}

    @PostMapping
    public ResponseEntity<IntegrationIndustryResponse.IntegrationIndustryInfoResponse> createIndustry(@RequestBody @Validated CreateIndustryRequest request) {
        return ResponseEntity.ok(industryService.save(new IntegrationIndustryRequest.IntegrationIndustryCreateRequest(request.industryName(), request.industryInformation())));
    }

    @GetMapping("/{industryId}")
    public ResponseEntity<IntegrationIndustryResponse.IntegrationIndustryInfoResponse> getIndustry(@PathVariable Long industryId) {
        return ResponseEntity.ok(industryService.get(industryId));
    }
}
