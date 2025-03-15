package org.myeonjeobjjang.domain.core.conversation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myeonjeobjjang.domain.common.BaseEntity;
import org.myeonjeobjjang.domain.core.coverLetter.entity.CoverLetter;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.domain.core.resume.entity.Resume;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Conversation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conversationId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    private CoverLetter coverLetter;
    @ManyToOne(fetch = FetchType.LAZY)
    private Resume resume;
    @Column(columnDefinition = "TEXT")
    private String industryInfo;
    @Column(columnDefinition = "TEXT")
    private String companyInfo;
    @Column(columnDefinition = "TEXT")
    private String jobDescriptionInfo;

    @Builder
    private Conversation(
        Member member,
        CoverLetter coverLetter,
        Resume resume,
        String industryInfo,
        String companyInfo,
        String jobDescriptionInfo
    ) {
        this.member = member;
        this.coverLetter = coverLetter;
        this.resume = resume;
        this.industryInfo = industryInfo;
        this.companyInfo = companyInfo;
        this.jobDescriptionInfo = jobDescriptionInfo;
    }
}

