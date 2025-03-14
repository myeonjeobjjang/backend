package org.myeonjeobjjang.applicant.company;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.company.service.CompanyService;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyResponse.CompanyInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicant/companies")
@RequiredArgsConstructor
public class CompanyApplicantController {
    private final CompanyService companyService;

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyInfoResponse> getCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(companyService.get(companyId));
    }

    @GetMapping("/industries/{industryId}")
    public ResponseEntity<Page<CompanyInfoResponse>> getCompanyByIndustry(@PathVariable Long industryId, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(companyService.getCompanyByIndustryId(industryId, PageRequest.of(page, size)));
    }
}
