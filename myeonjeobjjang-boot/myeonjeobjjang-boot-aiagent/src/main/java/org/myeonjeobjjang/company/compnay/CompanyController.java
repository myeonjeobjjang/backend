package org.myeonjeobjjang.company.compnay;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.company.service.CompanyService;
import org.myeonjeobjjang.domain.core.company.service.dto.IntegrationCompanyRequest;
import org.myeonjeobjjang.domain.core.company.service.dto.IntegrationCompanyResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/company/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<Boolean> getTrue() {
        return ResponseEntity.ok(true);
    }

    public record CreateCompanyRequest(
        @NotEmpty
        String companyName,
        @NotEmpty
        String companyInformation,
        @Positive
        Long industryId
    ) {
    }

    @PostMapping
    public ResponseEntity<IntegrationCompanyResponse.IntegrationCompanyInfoResponse> createCompany(@RequestBody @Validated CreateCompanyRequest request) {
        return ResponseEntity.ok(companyService.create(new IntegrationCompanyRequest.IntegrationCompanyCreateRequest(request.companyName(), request.companyInformation(), request.industryId())));
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<IntegrationCompanyResponse.IntegrationCompanyInfoResponse> getCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(companyService.get(companyId));
    }

    @GetMapping("/industries/{industryId}")
    public ResponseEntity<Page<IntegrationCompanyResponse.IntegrationCompanyInfoResponse>> getCompanyByIndustry(@PathVariable Long industryId, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(companyService.getCompanyByIndustryId(industryId, PageRequest.of(page, size)));
    }
}
