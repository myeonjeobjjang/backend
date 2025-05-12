package org.myeonjeobjjang.company.compnay.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.myeonjeobjjang.domain.core.company.service.dto.CompanyRequest.CompanyEditRequest;

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

    public record EditCompanyRequest(
        String companyName,
        String companyInformation,
        Long industryId
    ) {
        public CompanyEditRequest toDto() {
            return new CompanyEditRequest(companyName(), companyInformation(), industryId());
        }
    }
}
