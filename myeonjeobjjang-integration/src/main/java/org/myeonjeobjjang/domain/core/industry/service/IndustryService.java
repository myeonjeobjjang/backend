package org.myeonjeobjjang.domain.core.industry.service;

import org.myeonjeobjjang.domain.core.industry.entity.Industry;
import org.myeonjeobjjang.domain.core.industry.service.dto.IndustryRequest.IndustryCreateRequest;
import org.myeonjeobjjang.domain.core.industry.service.dto.IndustryResponse.IndustryInfoResponse;

public interface IndustryService {
    IndustryInfoResponse save(IndustryCreateRequest request);

    IndustryInfoResponse get(Long industryId);

    Industry findById(Long industryId);
}
