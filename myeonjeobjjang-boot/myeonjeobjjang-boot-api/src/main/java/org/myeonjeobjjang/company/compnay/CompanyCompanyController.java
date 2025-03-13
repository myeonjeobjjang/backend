package org.myeonjeobjjang.company.compnay;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.company.service.CompanyService;
import org.myeonjeobjjang.domain.core.company.service.dto.IntegrationCompanyRequest;
import org.myeonjeobjjang.domain.core.company.service.dto.IntegrationCompanyResponse;
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
}
