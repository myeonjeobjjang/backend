package org.myeonjeobjjang.domain.core.industry.service.dto;

import org.myeonjeobjjang.domain.core.industry.entity.Industry;

import java.util.List;

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

    public record IndustryInfoResponses(
        List<IndustryInfoResponse> industries
    ) {
        public static IndustryInfoResponses toDto(List<Industry> industryList) {
            return new IndustryInfoResponses(
                industryList.stream().map(IndustryInfoResponse::toDto).toList()
            );
        }
    }
}
