package org.myeonjeobjjang.domain.core.company.service;

import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyRequest.CompanyCreateRequest;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyRequest.CompanyEditRequest;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyResponse.CompanyInfoResponse;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyResponse.CompanyInfoResponses;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    CompanyInfoResponse create(CompanyCreateRequest request, Member actor);

    CompanyInfoResponse get(Long companyId);

    CompanyInfoResponses getMyCompanies(Member actor);

    Page<CompanyInfoResponse> getCompanyByIndustryId(Long industryId, Pageable pageable);

    Company findById(Long companyId);

    CompanyInfoResponse editCompanyInfo(Member member, Long companyId, CompanyEditRequest request);
}
