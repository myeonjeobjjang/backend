package org.myeonjeobjjang.domain.core.coverLetterItem.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myeonjeobjjang.domain.common.BaseEntity;
import org.myeonjeobjjang.domain.core.coverLetter.entity.CoverLetter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoverLetterItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coverLetterItemId;
    @Column(nullable = false)
    private Long questionNumber;
    @Column(columnDefinition = "TEXT")
    private String question;
    @Column(columnDefinition = "TEXT")
    private String answer;
    @ManyToOne(fetch = FetchType.LAZY)
    private CoverLetter coverLetter;

    @Builder
    public CoverLetterItem(Long questionNumber, String question, CoverLetter coverLetter) {
        this.questionNumber = questionNumber;
        this.question = (question != null ? question : "");
        this.answer = "";
        this.coverLetter = coverLetter;
    }

    public void updateInfo(String question, String answer) {
        if(question != null)
            this.question = question;
        if(answer != null)
            this.answer = answer;
    }
}

