package org.myeonjeobjjang.company.compnay;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.company.compnay.dto.CompanyCompanyRequest.CreateCompanyRequest;
import org.myeonjeobjjang.domain.core.company.service.CompanyService;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyResponse;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company/companies")
@RequiredArgsConstructor
public class CompanyCompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse.CompanyInfoResponse> createCompany(@RequestBody @Validated CreateCompanyRequest request) {
        return ResponseEntity.ok(companyService.create(new CompanyRequest.CompanyCreateRequest(request.companyName(), request.companyInformation(), request.industryId())));
    }
}
