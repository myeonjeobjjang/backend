package org.myeonjeobjjang.applicant.company;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.company.service.CompanyService;
import org.myeonjeobjjang.domain.core.company.service.dto.IntegrationCompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company/companies")
@RequiredArgsConstructor
public class CompanyApplicantController {
    private final CompanyService companyService;

    @GetMapping("/{companyId}")
    public ResponseEntity<IntegrationCompanyResponse.IntegrationCompanyInfoResponse> getCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(companyService.get(companyId));
    }

    @GetMapping("/industries/{industryId}")
    public ResponseEntity<Page<IntegrationCompanyResponse.IntegrationCompanyInfoResponse>> getCompanyByIndustry(@PathVariable Long industryId, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(companyService.getCompanyByIndustryId(industryId, PageRequest.of(page, size)));
    }
}
