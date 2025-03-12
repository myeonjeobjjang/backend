package org.myeonjeobjjang.domain.core.industry.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.industry.entity.Industry;
import org.myeonjeobjjang.domain.core.industry.repository.IndustryRepository;
import org.myeonjeobjjang.domain.core.industry.service.dto.IntegrationIndustryRequest;
import org.myeonjeobjjang.domain.core.industry.service.dto.IntegrationIndustryResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndustryServiceImpl implements IndustryService {
    private final IndustryRepository industryRepository;

    public IntegrationIndustryResponse.IntegrationIndustryInfoResponse save(IntegrationIndustryRequest.IntegrationIndustryCreateRequest request) {
        Industry newIndustry = Industry.builder()
            .industryName(request.industryName())
            .industryInformation(request.industryInformation())
            .build();
        Industry savedIndustry = industryRepository.save(newIndustry);
        return new IntegrationIndustryResponse.IntegrationIndustryInfoResponse(savedIndustry.getIndustryId(), savedIndustry.getIndustryName(), savedIndustry.getIndustryInformation());
    }

    public IntegrationIndustryResponse.IntegrationIndustryInfoResponse get(Long industryId) {
        Industry industry = industryRepository.findById(industryId)
            .orElseThrow();
        return new IntegrationIndustryResponse.IntegrationIndustryInfoResponse(industry.getIndustryId(), industry.getIndustryName(), industry.getIndustryInformation());
    }
}
