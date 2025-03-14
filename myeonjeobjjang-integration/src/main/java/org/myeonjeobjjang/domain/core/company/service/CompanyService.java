package org.myeonjeobjjang.domain.core.company.service;

import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyRequest.CompanyCreateRequest;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyResponse.CompanyInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    CompanyInfoResponse create(CompanyCreateRequest request);

    CompanyInfoResponse get(Long companyId);

    Page<CompanyInfoResponse> getCompanyByIndustryId(Long industryId, Pageable pageable);

    Company findById(Long companyId);
}
