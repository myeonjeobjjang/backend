package org.myeonjeobjjang.domain.core.company.repository.dto;

public class CompanyProjection {
    public interface CompanyInfoProjection {
        Long getCompanyId();
        String getCompanyName();
        String getCompanyInformation();
        Long getIndustryId();
    }
}
