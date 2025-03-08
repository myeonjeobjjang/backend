package org.myeonjeobjjang.domain.core.jobPosting.repository;

import org.myeonjeobjjang.domain.core.jobPosting.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepository extends JpaRepository<JobPosting,Long> {
}
