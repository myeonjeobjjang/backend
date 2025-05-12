package org.myeonjeobjjang.domain.core.company.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.company.repository.CompanyRepository;
import org.myeonjeobjjang.domain.core.company.repository.dto.CompanyProjection.CompanyInfoProjection;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyRequest.CompanyCreateRequest;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyRequest.CompanyEditRequest;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyResponse.CompanyInfoResponse;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyResponse.CompanyInfoResponses;
import org.myeonjeobjjang.domain.core.companyAdministrator.entity.CompanyAdministrator;
import org.myeonjeobjjang.domain.core.companyAdministrator.repository.CompanyAdministratorRepository;
import org.myeonjeobjjang.domain.core.industry.entity.Industry;
import org.myeonjeobjjang.domain.core.industry.repository.IndustryRepository;
import org.myeonjeobjjang.domain.core.industry.service.IndustryService;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.domain.core.member.entity.Role;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.myeonjeobjjang.config.security.SecurityErrorCode.NO_PERMISSION;
import static org.myeonjeobjjang.domain.core.company.CompanyErrorCode.*;
import static org.myeonjeobjjang.domain.core.industry.IndustryErrorCode.INDUSTRY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyAdministratorRepository companyAdministratorRepository;
    private final IndustryRepository industryRepository;

    private final IndustryService industryService;

    @Transactional
    public CompanyInfoResponse create(CompanyCreateRequest request, Member actor) {
        if (companyRepository.findCompanyByCompanyName(request.companyName()).isPresent())
            throw new BaseException(DUPLICATED_COMPANY_NAME);
        Industry industry = industryService.findById(request.industryId());
        Company company = companyRepository.save(request.toEntity(industry));
        if (actor.getRole().equals(Role.COMPANY))
            companyAdministratorRepository.save(CompanyAdministrator.builder().company(company).administrator(actor).build());
        return CompanyInfoResponse.toDto(company);
    }

    public CompanyInfoResponse get(Long companyId) {
        Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new BaseException(COMPANY_NOT_FOUND));
        return CompanyInfoResponse.toDto(company);
    }

    public CompanyInfoResponses getMyCompanies(Member actor) {
        List<Company> companyList;
        if (actor.getRole().equals(Role.COMPANY)) {
            companyList = companyAdministratorRepository.findAllByAdministrator(actor);
        } else if (actor.getRole().equals(Role.ADMIN)) {
            companyList = companyRepository.findAll();
        } else {
            throw new BaseException(NO_PERMISSION);
        }
        return CompanyInfoResponses.toDto(companyList);
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

    @Override
    @Transactional
    public CompanyInfoResponse editCompanyInfo(Member actor, Long companyId, CompanyEditRequest request) {
        Company company = findById(companyId);

        if (actor.getRole().equals(Role.COMPANY)) {
            if (!companyAdministratorRepository.existsByAdministratorAndCompany(actor, company)) {
                throw new BaseException(NOT_COMPANY_ADMINISTRATOR);
            }
        }

        Optional<Industry> optionalIndustry = industryRepository.findById(request.industryId());
        if (optionalIndustry.isEmpty()) {
            throw new BaseException(INDUSTRY_NOT_FOUND);
        }
        request.update(company, optionalIndustry.get());
        return CompanyInfoResponse.toDto(company);
    }
}
