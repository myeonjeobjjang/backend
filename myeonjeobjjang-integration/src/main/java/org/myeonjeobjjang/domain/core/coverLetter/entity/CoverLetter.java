package org.myeonjeobjjang.domain.core.coverLetter.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myeonjeobjjang.domain.common.BaseEntity;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.domain.core.jobPosting.entity.JobPosting;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoverLetter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;
    @ManyToOne(fetch = FetchType.LAZY)
    private JobPosting jobPosting;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    private CoverLetter(JobPosting recruitment, Member member) {
        this.jobPosting = recruitment;
        this.member = member;
    }
}

