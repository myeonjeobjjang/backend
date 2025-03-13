package org.myeonjeobjjang.domain.core.coverLetterItem.repository;

import org.myeonjeobjjang.domain.core.coverLetter.entity.CoverLetter;
import org.myeonjeobjjang.domain.core.coverLetterItem.entity.CoverLetterItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CoverLetterItemRepository extends JpaRepository<CoverLetterItem,Long> {
    @Query("""
    select count(cli.questionNumber)
    from CoverLetterItem cli
    where cli.coverLetter = :coverLetter
    """)
    long getLastQuestionNumberByCoverLetter(CoverLetter coverLetter);
}
