package org.myeonjeobjjang.admin.industry.dto;

import jakarta.validation.constraints.NotEmpty;

public class IndustryAdminRequest {
    public record CreateIndustryAdminRequest(
        @NotEmpty
        String industryName,
        @NotEmpty
        String industryInformation
    ) {}
}
