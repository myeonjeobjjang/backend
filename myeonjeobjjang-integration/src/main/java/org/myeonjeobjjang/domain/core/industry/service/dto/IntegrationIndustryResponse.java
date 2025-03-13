package org.myeonjeobjjang.domain.core.industry.service.dto;

public class IntegrationIndustryResponse {
    public record IntegrationIndustryInfoResponse(
        Long industryId,
        String industryName,
        String industryInformation
    ) {
    }
}
