package org.myeonjeobjjang.domain.core.jobPosting.repository;

import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.jobPosting.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JobPostingRepository extends JpaRepository<JobPosting,Long> {
    Optional<JobPosting> findDuplicateJobPostingByCompanyAndJobPostingName(Company company, String postingName);

    @Query("""
    select jp
    from JobPosting jp
    where
    jp.company = :company
    and
    :currentDateTime < jp.duedate
    """)
    List<JobPosting> findJobPostingByCompanyAndDuedateAfter(Company company, LocalDateTime currentDateTime);
}
