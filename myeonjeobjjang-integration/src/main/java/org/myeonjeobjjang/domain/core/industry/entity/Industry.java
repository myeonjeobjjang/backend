package org.myeonjeobjjang.domain.core.industry.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myeonjeobjjang.domain.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Industry extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long industryId;
    private String industryName;
    @Column(columnDefinition = "TEXT")
    private String industryInformation;

    @Builder
    private Industry(String industryName, String industryInformation) {
        this.industryName = industryName;
        this.industryInformation = industryInformation;
    }
}
