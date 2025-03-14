package org.myeonjeobjjang.domain.core.industry.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.industry.entity.Industry;
import org.myeonjeobjjang.domain.core.industry.repository.IndustryRepository;
import org.myeonjeobjjang.domain.core.industry.service.dto.IndustryRequest.IndustryCreateRequest;
import org.myeonjeobjjang.domain.core.industry.service.dto.IndustryResponse.IndustryInfoResponse;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.stereotype.Service;

import static org.myeonjeobjjang.domain.core.industry.IndustryErrorCode.DUPLICATED_INDUSTRY_NAME;
import static org.myeonjeobjjang.domain.core.industry.IndustryErrorCode.INDUSTRY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class IndustryServiceImpl implements IndustryService {
    private final IndustryRepository industryRepository;

    public IndustryInfoResponse save(IndustryCreateRequest request) {
        if (industryRepository.findIndustryByIndustryName(request.industryName()).isPresent())
            throw new BaseException(DUPLICATED_INDUSTRY_NAME);
        Industry newIndustry = request.toEntity();
        Industry savedIndustry = industryRepository.save(newIndustry);
        return IndustryInfoResponse.toDto(savedIndustry);
    }

    public IndustryInfoResponse get(Long industryId) {
        Industry industry = industryRepository.findById(industryId)
            .orElseThrow(() -> new BaseException(INDUSTRY_NOT_FOUND));
        return IndustryInfoResponse.toDto(industry);
    }

    public Industry findById(Long industryId) {
        return industryRepository.findById(industryId)
            .orElseThrow(() -> new BaseException(INDUSTRY_NOT_FOUND));
    }
}
