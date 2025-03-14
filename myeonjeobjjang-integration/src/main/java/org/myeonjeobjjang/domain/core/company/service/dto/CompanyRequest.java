package org.myeonjeobjjang.domain.core.company.service.dto;

import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.industry.entity.Industry;

public class CompanyRequest {
    public record CompanyCreateRequest(
        String companyName,
        String companyInformation,
        Long industryId
    ) {
        public Company toEntity(Industry industry) {
            return Company.builder()
                .companyName(companyName)
                .companyInformation(companyInformation)
                .industry(industry)
                .build();
        }
    }
}
