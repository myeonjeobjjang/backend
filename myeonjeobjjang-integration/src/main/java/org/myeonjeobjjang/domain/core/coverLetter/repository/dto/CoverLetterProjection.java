package org.myeonjeobjjang.domain.core.coverLetter.repository.dto;

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
}
