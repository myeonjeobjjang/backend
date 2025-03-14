package org.myeonjeobjjang.domain.core.company.repository;

import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.company.repository.dto.CompanyProjection;
import org.myeonjeobjjang.domain.core.company.repository.dto.CompanyProjection.CompanyInfoProjection;
import org.myeonjeobjjang.domain.core.industry.entity.Industry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findCompanyByCompanyName(String companyName);

    @Query("""
        select
        c.companyId companyId,
        c.companyName companyName,
        c.companyInformation companyInformation,
        c.industry.industryId industryId
        from Company c
        where c.industry = :industry
        """)
    Page<CompanyInfoProjection> findAllCompaniesByIndustry(Industry industry, Pageable pageable);
}
