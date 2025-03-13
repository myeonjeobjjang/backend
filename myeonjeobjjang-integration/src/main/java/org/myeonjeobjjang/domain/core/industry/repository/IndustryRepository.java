package org.myeonjeobjjang.domain.core.industry.repository;

import org.myeonjeobjjang.domain.core.industry.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndustryRepository extends JpaRepository<Industry, Long> {
    Optional<Industry> findIndustryByIndustryName(String industryName);
}
