package org.myeonjeobjjang.domain.core.industry.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.industry.entity.Industry;
import org.myeonjeobjjang.domain.core.industry.repository.IndustryRepository;
import org.myeonjeobjjang.domain.core.industry.service.dto.IntegrationIndustryRequest;
import org.myeonjeobjjang.domain.core.industry.service.dto.IntegrationIndustryResponse;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.stereotype.Service;

import static org.myeonjeobjjang.domain.core.industry.IndustryErrorCode.DUPLICATED_INDUSTRY_NAME;
import static org.myeonjeobjjang.domain.core.industry.IndustryErrorCode.INDUSTRY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class IndustryServiceImpl implements IndustryService {
    private final IndustryRepository industryRepository;

    public IntegrationIndustryResponse.IntegrationIndustryInfoResponse save(IntegrationIndustryRequest.IntegrationIndustryCreateRequest request) {
        if(industryRepository.findIndustryByIndustryName(request.industryName()).isPresent())
            throw new BaseException(DUPLICATED_INDUSTRY_NAME);
        Industry newIndustry = Industry.builder()
            .industryName(request.industryName())
            .industryInformation(request.industryInformation())
            .build();
        Industry savedIndustry = industryRepository.save(newIndustry);
        return new IntegrationIndustryResponse.IntegrationIndustryInfoResponse(savedIndustry.getIndustryId(), savedIndustry.getIndustryName(), savedIndustry.getIndustryInformation());
    }

    public IntegrationIndustryResponse.IntegrationIndustryInfoResponse get(Long industryId) {
        Industry industry = industryRepository.findById(industryId)
            .orElseThrow(() -> new BaseException(INDUSTRY_NOT_FOUND));
        return new IntegrationIndustryResponse.IntegrationIndustryInfoResponse(industry.getIndustryId(), industry.getIndustryName(), industry.getIndustryInformation());
    }
}
