package org.myeonjeobjjang.domain.core.industry.service.dto;

import org.myeonjeobjjang.domain.core.industry.entity.Industry;

public class IndustryRequest {
    public record IndustryCreateRequest(
        String industryName,
        String industryInformation
    ) {
        public Industry toEntity() {
            return Industry.builder()
                .industryName(industryName)
                .industryInformation(industryInformation)
                .build();
        }
    }
}
