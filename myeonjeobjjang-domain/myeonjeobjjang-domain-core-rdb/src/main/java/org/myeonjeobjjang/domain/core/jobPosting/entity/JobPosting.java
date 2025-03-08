package org.myeonjeobjjang.domain.core.jobPosting.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myeonjeobjjang.domain.common.BaseEntity;
import org.myeonjeobjjang.domain.core.company.entity.Company;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobPosting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruitmentId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;
    private String jobPostingName;
    @Column(columnDefinition = "TEXT")
    private String jobPostingDescription;
    private LocalDateTime duedate;

    @Builder
    private JobPosting(Company company, String jobPostingName, String jobPostingDescription, LocalDateTime duedate) {
        this.company = company;
        this.jobPostingName = jobPostingName;
        this.jobPostingDescription = jobPostingDescription;
        this.duedate = duedate;
    }
}
