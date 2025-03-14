package org.myeonjeobjjang.domain.core.company.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.company.repository.CompanyRepository;
import org.myeonjeobjjang.domain.core.company.repository.dto.CompanyProjection.CompanyInfoProjection;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyRequest.CompanyCreateRequest;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyResponse.CompanyInfoResponse;
import org.myeonjeobjjang.domain.core.industry.entity.Industry;
import org.myeonjeobjjang.domain.core.industry.service.IndustryService;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.myeonjeobjjang.domain.core.company.CompanyErrorCode.COMPANY_NOT_FOUND;
import static org.myeonjeobjjang.domain.core.company.CompanyErrorCode.DUPLICATED_COMPANY_NAME;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    private final IndustryService industryService;

    public CompanyInfoResponse create(CompanyCreateRequest request) {
        if (companyRepository.findCompanyByCompanyName(request.companyName()).isPresent())
            throw new BaseException(DUPLICATED_COMPANY_NAME);
        Industry industry = industryService.findById(request.industryId());
        Company newCompany = request.toEntity(industry);
        Company savedCompany = companyRepository.save(newCompany);
        return CompanyInfoResponse.toDto(savedCompany);
    }

    public CompanyInfoResponse get(Long companyId) {
        Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new BaseException(COMPANY_NOT_FOUND));
        return CompanyInfoResponse.toDto(company);
    }

    @Override
    public Page<CompanyInfoResponse> getCompanyByIndustryId(Long industryId, Pageable pageable) {
        Industry industry = industryService.findById(industryId);
        Page<CompanyInfoProjection> companyInfoProjectionPage = companyRepository.findAllCompaniesByIndustry(industry, pageable);

        return new PageImpl<>(companyInfoProjectionPage.getContent().stream()
            .map(CompanyInfoResponse::toDto).toList(),
            companyInfoProjectionPage.getPageable(), companyInfoProjectionPage.getTotalElements());
    }

    @Override
    public Company findById(Long companyId) {
        return companyRepository.findById(companyId)
            .orElseThrow(() -> new BaseException(COMPANY_NOT_FOUND));
    }
}
