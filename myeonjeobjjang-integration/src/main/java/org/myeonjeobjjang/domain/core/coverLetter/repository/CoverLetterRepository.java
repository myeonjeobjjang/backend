package org.myeonjeobjjang.domain.core.coverLetter.repository;

import org.myeonjeobjjang.domain.core.coverLetter.entity.CoverLetter;
import org.myeonjeobjjang.domain.core.coverLetter.repository.dto.CoverLetterProjection;
import org.myeonjeobjjang.domain.core.coverLetter.repository.dto.CoverLetterProjection.CoverLetterInfoForConversationProjection;
import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CoverLetterRepository extends JpaRepository<CoverLetter,Long> {
    Optional<CoverLetter> findByMemberAndJobDescription(Member member, JobDescription jobDescription);

    @Query("""
    select
    cl.coverLetterId coverLetterId,
    jd.jobName jobName,
    jd.description description,
    cli.coverLetterItemId coverLetterItemId,
    cli.questionNumber questionNumber,
    cli.question question,
    cli.answer answer
    from CoverLetter cl
    left join JobDescription jd
    on cl.jobDescription = jd
    left join CoverLetterItem cli
    on cl = cli.coverLetter
    where cl.coverLetterId = :coverLetterId
    order by cli.questionNumber
    """)
    List<CoverLetterProjection.CoverLetterInfoProjection> findByCoverLetterId(Long coverLetterId);

    @Query("""
    select
    cl coverLetter,
    jd.jobName jobName,
    jd.description description,
    c.companyName companyName,
    c.companyInformation companyInformation,
    i.industryName industryName,
    i.industryInformation industryInformation
    from CoverLetter cl
    left join JobDescription jd
    on cl.jobDescription = jd
    left join JobPosting jp
    on jd.jobPosting = jp
    left join Company c
    on jp.company = c
    left join Industry i
    on c.industry = i
    where cl.coverLetterId = :coverLetterId
    """)
    Optional<CoverLetterInfoForConversationProjection> findCoverLetterForConversation(Long coverLetterId);
}
