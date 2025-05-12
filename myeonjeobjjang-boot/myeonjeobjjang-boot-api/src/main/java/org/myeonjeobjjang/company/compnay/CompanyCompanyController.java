package org.myeonjeobjjang.company.compnay;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.company.compnay.dto.CompanyCompanyRequest.CreateCompanyRequest;
import org.myeonjeobjjang.company.compnay.dto.CompanyCompanyRequest.EditCompanyRequest;
import org.myeonjeobjjang.config.security.PrincipalDetails;
import org.myeonjeobjjang.domain.core.company.service.CompanyService;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyRequest;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyResponse.CompanyInfoResponse;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyResponse.CompanyInfoResponses;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.myeonjeobjjang.company.CompanyCompanyValidateError.INDUSTRY_MUST_POSITIVE;

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

    @PatchMapping("/{companyId}")
    public ResponseEntity<CompanyInfoResponse> editCompany(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long companyId,
        @RequestBody @Validated EditCompanyRequest request
    ) {
        Member member = principalDetails.getMember();
        if(request.industryId() < 0) {
            throw new BaseException(INDUSTRY_MUST_POSITIVE);
        }
        return ResponseEntity.ok(companyService.editCompanyInfo(member, companyId, request.toDto()));
    }
}
