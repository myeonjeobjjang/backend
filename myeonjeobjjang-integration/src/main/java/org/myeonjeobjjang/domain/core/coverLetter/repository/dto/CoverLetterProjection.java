package org.myeonjeobjjang.domain.core.coverLetter.repository.dto;

import org.myeonjeobjjang.domain.core.coverLetter.entity.CoverLetter;

public class CoverLetterProjection {
    public interface CoverLetterInfoProjection {
        Long getCoverLetterId();
        String getJobName();
        String getDescription();
        Long getCoverLetterItemId();
        Long getQuestionNumber();
        String getQuestion();
        String getAnswer();
    }
    public interface CoverLetterInfoForConversationProjection {
        CoverLetter getCoverLetter();
        String getJobName();
        String getDescription();
        String getCompanyName();
        String getCompanyInformation();
        String getIndustryName();
        String getIndustryInformation();
    }
}
