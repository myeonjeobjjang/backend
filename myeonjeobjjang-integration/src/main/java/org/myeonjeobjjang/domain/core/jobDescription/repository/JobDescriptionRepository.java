package org.myeonjeobjjang.domain.core.jobDescription.repository;

import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.jobPosting.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JobDescriptionRepository extends JpaRepository<JobDescription,Long> {
    List<JobDescription> findJobDescriptionsByJobPosting(JobPosting jobPosting);

    @Query("""
    select jd
    from JobDescription jd
    where
    jd.jobPosting = :jobPosting
    and
    jd.jobName = :jobName
    """)
    Optional<Object> findJobDescriptionByJobPostingAndJobName(JobPosting jobPosting, String jobName);
}
