package org.myeonjeobjjang.company.compnay.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public class CompanyCompanyRequest {
    public record CreateCompanyRequest(
        @NotEmpty
        String companyName,
        @NotEmpty
        String companyInformation,
        @Positive
        Long industryId
    ) {
    }
}
