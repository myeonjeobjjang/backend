package org.myeonjeobjjang.domain.core.jobDescriptionQuestion.entity;

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
public class JobDescriptionQuestion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobDescriptionQuestionId;
    @Column(nullable = false)
    private Long questionNumber;
    @Column(columnDefinition = "TEXT")
    private String question;
    @ManyToOne(fetch = FetchType.LAZY)
    private JobDescription jobDescription;

    @Builder
    private JobDescriptionQuestion(Long questionNumber,String question, JobDescription jobDescription) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.jobDescription = jobDescription;
    }
}
