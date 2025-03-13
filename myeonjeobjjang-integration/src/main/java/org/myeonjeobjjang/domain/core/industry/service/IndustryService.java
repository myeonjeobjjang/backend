package org.myeonjeobjjang.domain.core.industry.service;

import org.myeonjeobjjang.domain.core.industry.entity.Industry;
import org.myeonjeobjjang.domain.core.industry.service.dto.IntegrationIndustryRequest;
import org.myeonjeobjjang.domain.core.industry.service.dto.IntegrationIndustryResponse;

public interface IndustryService {
    IntegrationIndustryResponse.IntegrationIndustryInfoResponse save(IntegrationIndustryRequest.IntegrationIndustryCreateRequest request);
    IntegrationIndustryResponse.IntegrationIndustryInfoResponse get(Long industryId);

    Industry findById(Long industryId);
}
