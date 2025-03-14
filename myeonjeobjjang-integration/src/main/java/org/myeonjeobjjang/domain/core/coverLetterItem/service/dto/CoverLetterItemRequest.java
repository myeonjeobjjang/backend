package org.myeonjeobjjang.domain.core.coverLetterItem.service.dto;

import org.myeonjeobjjang.domain.core.coverLetter.entity.CoverLetter;
import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;

public class CoverLetterItemRequest {
    public record CoverLetterItemCopyFromJobDescriptionRequest(
        CoverLetter coverLetter,
        JobDescription jobDescription
    ) {
        public static CoverLetterItemCopyFromJobDescriptionRequest toDto(
            CoverLetter coverLetter,
            JobDescription jobDescription
        ) {
            return new CoverLetterItemCopyFromJobDescriptionRequest(coverLetter, jobDescription);
        }
    }

    public record CoverLetterItemUpdateRequest(
        Long coverLetterItemId,
        String question,
        String answer
    ) {
    }
}
