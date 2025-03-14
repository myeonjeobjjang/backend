package org.myeonjeobjjang.domain.core.industry.service.dto;

import org.myeonjeobjjang.domain.core.industry.entity.Industry;

public class IndustryResponse {
    public record IndustryInfoResponse(
        Long industryId,
        String industryName,
        String industryInformation
    ) {
        public static IndustryInfoResponse toDto(Industry industry) {
            return new IndustryInfoResponse(
                industry.getIndustryId(),
                industry.getIndustryName(),
                industry.getIndustryInformation()
            );
        }
    }
}
