package org.myeonjeobjjang.domain.core.company.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.company.repository.CompanyRepository;
import org.myeonjeobjjang.domain.core.company.repository.dto.CompanyProjection;
import org.myeonjeobjjang.domain.core.company.service.dto.IntegrationCompanyRequest;
import org.myeonjeobjjang.domain.core.company.service.dto.IntegrationCompanyResponse;
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

    public IntegrationCompanyResponse.IntegrationCompanyInfoResponse create(IntegrationCompanyRequest.IntegrationCompanyCreateRequest request) {
        if (companyRepository.findCompanyByCompanyName(request.companyName()).isPresent())
            throw new BaseException(DUPLICATED_COMPANY_NAME);
        Industry industry = industryService.findById(request.industryId());
        Company newCompany = Company.builder()
            .companyName(request.companyName())
            .companyInformation(request.companyInformation())
            .industry(industry)
            .build();
        Company savedCompany = companyRepository.save(newCompany);
        return new IntegrationCompanyResponse.IntegrationCompanyInfoResponse(savedCompany.getCompanyId(), savedCompany.getCompanyName(), savedCompany.getCompanyInformation(), savedCompany.getIndustry().getIndustryId());
    }

    public IntegrationCompanyResponse.IntegrationCompanyInfoResponse get(Long companyId) {
        Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new BaseException(COMPANY_NOT_FOUND));
        return new IntegrationCompanyResponse.IntegrationCompanyInfoResponse(company.getCompanyId(), company.getCompanyName(), company.getCompanyInformation(), company.getIndustry().getIndustryId());
    }

    @Override
    public Page<IntegrationCompanyResponse.IntegrationCompanyInfoResponse> getCompanyByIndustryId(Long industryId, Pageable pageable) {
        Industry industry = industryService.findById(industryId);
        Page<CompanyProjection.CompanyInfoProjection> companyInfoProjectionPage = companyRepository.findAllCompaniesByIndustry(industry, pageable);

        return new PageImpl<>(companyInfoProjectionPage.getContent().stream().map(projection
            -> new IntegrationCompanyResponse.IntegrationCompanyInfoResponse(
            projection.getCompanyId(),
            projection.getCompanyName(),
            projection.getCompanyInformation(),
            projection.getIndustryId())
        ).toList(), companyInfoProjectionPage.getPageable(), companyInfoProjectionPage.getTotalElements());
    }

    @Override
    public Company findById(Long companyId) {
        return companyRepository.findById(companyId)
            .orElseThrow(() -> new BaseException(COMPANY_NOT_FOUND));
    }
}
