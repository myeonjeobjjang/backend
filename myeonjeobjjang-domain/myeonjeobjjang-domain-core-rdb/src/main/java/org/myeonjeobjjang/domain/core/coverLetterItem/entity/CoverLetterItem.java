package org.myeonjeobjjang.domain.core.coverLetterItem.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
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
    private Long applicationItemId;
    @Column(columnDefinition = "TEXT")
    private String question;
    @Column(columnDefinition = "TEXT")
    private String answer;
    @ManyToOne(fetch = FetchType.LAZY)
    private CoverLetter application;

    public CoverLetterItem(String question, String answer, CoverLetter application) {
        this.question = question;
        this.answer = answer;
        this.application = application;
    }
}

