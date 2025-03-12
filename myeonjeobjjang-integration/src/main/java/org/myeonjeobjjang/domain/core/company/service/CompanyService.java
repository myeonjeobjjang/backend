package org.myeonjeobjjang.domain.core.company.service;

import org.myeonjeobjjang.domain.core.company.service.dto.IntegrationCompanyRequest;
import org.myeonjeobjjang.domain.core.company.service.dto.IntegrationCompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    IntegrationCompanyResponse.IntegrationCompanyInfoResponse create(IntegrationCompanyRequest.IntegrationCompanyCreateRequest request);

    IntegrationCompanyResponse.IntegrationCompanyInfoResponse get(Long companyId);

    Page<IntegrationCompanyResponse.IntegrationCompanyInfoResponse> getCompanyByIndustryId(Long industryId, Pageable pageable);
}
