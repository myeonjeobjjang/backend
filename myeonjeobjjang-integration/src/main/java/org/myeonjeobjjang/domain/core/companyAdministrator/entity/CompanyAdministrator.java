package org.myeonjeobjjang.domain.core.companyAdministrator.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyAdministrator {
    @Id
    @Column(name = "company_administrator_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyAdministratorId;
    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;
    @ManyToOne
    @JoinColumn(name = "administratorId")
    private Member administrator;

    @Builder
    private CompanyAdministrator(Company company, Member administrator) {
        this.company = company;
        this.administrator = administrator;
    }
}
