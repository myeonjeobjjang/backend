package org.myeonjeobjjang.domain.core.company.service.dto;

import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.company.repository.dto.CompanyProjection.CompanyInfoProjection;

import java.util.List;

public class CompanyResponse {
    public record CompanyInfoResponse(
        Long companyId,
        String companyName,
        String companyInformation,
        Long industryId
    ) {
        public static CompanyInfoResponse toDto(Company company) {
            return new CompanyInfoResponse(
                company.getCompanyId(),
                company.getCompanyName(),
                company.getCompanyInformation(),
                company.getIndustry().getIndustryId()
            );
        }

        public static CompanyInfoResponse toDto(CompanyInfoProjection projection) {
            return new CompanyInfoResponse(
                projection.getCompanyId(),
                projection.getCompanyName(),
                projection.getCompanyInformation(),
                projection.getIndustryId()
            );
        }
    }

    public record CompanyInfoResponses(
        List<CompanyInfoResponse> companies
    ) {
        public static CompanyInfoResponses toDto(List<Company> companyList) {
            return new CompanyInfoResponses(companyList.stream()
                .map(CompanyInfoResponse::toDto).toList());
        }
    }
}
