package org.myeonjeobjjang.domain.core.industry.service.dto;

public class IntegrationIndustryRequest {
    public record IntegrationIndustryCreateRequest(
        String industryName,
        String industryInformation
    ) {
    }
}
