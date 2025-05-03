package org.myeonjeobjjang.company.compnay;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.company.compnay.dto.CompanyCompanyRequest.CreateCompanyRequest;
import org.myeonjeobjjang.config.security.PrincipalDetails;
import org.myeonjeobjjang.domain.core.company.service.CompanyService;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyRequest;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyResponse.CompanyInfoResponse;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyResponse.CompanyInfoResponses;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company/companies")
@RequiredArgsConstructor
public class CompanyCompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyInfoResponse> createCompany(
        @RequestBody @Validated CreateCompanyRequest request,
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Member member = principalDetails.getMember();
        return ResponseEntity.ok(companyService.create(new CompanyRequest.CompanyCreateRequest(request.companyName(), request.companyInformation(), request.industryId()), member));
    }

    @GetMapping("/mine")
    public ResponseEntity<CompanyInfoResponses> getMyCompanies(
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Member member = principalDetails.getMember();
        return ResponseEntity.ok(companyService.getMyCompanies(member));
    }
}
