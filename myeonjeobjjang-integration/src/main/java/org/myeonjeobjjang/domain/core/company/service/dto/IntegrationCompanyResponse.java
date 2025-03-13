package org.myeonjeobjjang.domain.core.company.service.dto;

public class IntegrationCompanyResponse {
    public record IntegrationCompanyInfoResponse(
        Long companyId,
        String companyName,
        String companyInformation,
        Long industryId
    ) {
    }
}
