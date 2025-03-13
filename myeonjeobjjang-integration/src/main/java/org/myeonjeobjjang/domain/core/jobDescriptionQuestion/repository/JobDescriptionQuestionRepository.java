package org.myeonjeobjjang.domain.core.jobDescriptionQuestion.repository;

import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.entity.JobDescriptionQuestion;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobDescriptionQuestionRepository extends JpaRepository<JobDescriptionQuestion,Long> {
    List<JobDescriptionQuestion> findByJobDescription(JobDescription jobDescription, Sort sort);

    Optional<JobDescriptionQuestion> findJobDescriptionQuestionByJobDescriptionAndQuestionNumber(JobDescription jobDescription, Long questionNumber);
}
