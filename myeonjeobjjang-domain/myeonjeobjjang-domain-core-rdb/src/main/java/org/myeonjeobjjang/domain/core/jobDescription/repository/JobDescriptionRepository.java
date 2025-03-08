package org.myeonjeobjjang.domain.core.jobDescription.repository;

import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobDescriptionRepository extends JpaRepository<JobDescription,Long> {
}
