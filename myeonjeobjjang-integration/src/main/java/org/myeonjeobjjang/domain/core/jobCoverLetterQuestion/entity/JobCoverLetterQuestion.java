package org.myeonjeobjjang.domain.core.jobCoverLetterQuestion.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myeonjeobjjang.domain.common.BaseEntity;
import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobCoverLetterQuestion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruitmentQuestionId;
    @Column(columnDefinition = "TEXT")
    private String question;
    @ManyToOne(fetch = FetchType.LAZY)
    private JobDescription jobDescription;

    @Builder
    private JobCoverLetterQuestion(String question, JobDescription jobDescription) {
        this.question = question;
        this.jobDescription = jobDescription;
    }
}

