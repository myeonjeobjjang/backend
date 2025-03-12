package org.myeonjeobjjang.domain.core.company.repository;

import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}
