package org.myeonjeobjjang.domain.core.company.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myeonjeobjjang.domain.common.BaseEntity;
import org.myeonjeobjjang.domain.core.industry.entity.Industry;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;
    private String companyName;
    private String companyInformation;
    @ManyToOne(fetch = FetchType.LAZY)
    private Industry industry;

    @Builder
    private Company(String companyName, String companyInformation, Industry industry) {
        this.companyName = companyName;
        this.companyInformation = companyInformation;
        this.industry = industry;
    }
}
