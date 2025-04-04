package org.myeonjeobjjang.domain.core.coverLetter.repository.dto;

import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.coverLetter.entity.CoverLetter;
import org.myeonjeobjjang.domain.core.industry.entity.Industry;
import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.jobPosting.entity.JobPosting;

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
        JobDescription getJobDescription();
        JobPosting getJobPosting();
        Company getCompany();
        Industry getIndustry();
    }
}
