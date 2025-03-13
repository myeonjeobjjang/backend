package org.myeonjeobjjang.domain.core.coverLetter.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myeonjeobjjang.domain.common.BaseEntity;
import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoverLetter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coverLetterId;
    @ManyToOne(fetch = FetchType.LAZY)
    private JobDescription jobDescription;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    private CoverLetter(JobDescription jobDescription, Member member) {
        this.jobDescription = jobDescription;
        this.member = member;
    }
}

