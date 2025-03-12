package org.myeonjeobjjang.domain.core.jobDescription.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myeonjeobjjang.domain.common.BaseEntity;
import org.myeonjeobjjang.domain.core.jobPosting.entity.JobPosting;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobDescription extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobDescriptionId;
    private String jobName;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private JobPosting recruitment;

    @Builder
    private JobDescription(String jobName, String description, JobPosting recruitment) {
        this.jobName = jobName;
        this.description = description;
        this.recruitment = recruitment;
    }
}

