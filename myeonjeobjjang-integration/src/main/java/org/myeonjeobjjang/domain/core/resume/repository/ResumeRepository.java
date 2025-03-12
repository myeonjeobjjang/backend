package org.myeonjeobjjang.domain.core.resume.repository;

import org.myeonjeobjjang.domain.core.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
