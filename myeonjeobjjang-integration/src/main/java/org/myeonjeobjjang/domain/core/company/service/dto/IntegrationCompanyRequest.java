package org.myeonjeobjjang.domain.core.company.service.dto;

public class IntegrationCompanyRequest {
    public record IntegrationCompanyCreateRequest(
        String companyName,
        String companyInformation,
        Long industryId
    ) {}
}
