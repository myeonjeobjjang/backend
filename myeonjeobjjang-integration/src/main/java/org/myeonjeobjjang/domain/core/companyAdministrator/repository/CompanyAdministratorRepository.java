package org.myeonjeobjjang.domain.core.companyAdministrator.repository;

import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.companyAdministrator.entity.CompanyAdministrator;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyAdministratorRepository extends JpaRepository<CompanyAdministrator, Long> {
    @Query("""
        select c
        from CompanyAdministrator ca
        left join Company c
        on (ca.company = c)
        where ca.administrator = :administrator
        """)
    List<Company> findAllByAdministrator(Member administrator);
}
