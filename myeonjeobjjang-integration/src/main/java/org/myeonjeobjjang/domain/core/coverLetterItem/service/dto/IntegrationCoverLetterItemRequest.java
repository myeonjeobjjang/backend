package org.myeonjeobjjang.domain.core.coverLetterItem.service.dto;

import org.myeonjeobjjang.domain.core.coverLetter.entity.CoverLetter;
import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;

public class IntegrationCoverLetterItemRequest {
    public record IntegrationCoverLetterItemCopyFromJobDescriptionRequest(
        CoverLetter coverLetter,
        JobDescription jobDescription
    ) {
    }

    public record IntegrationCoverLetterItemUpdateRequest(
        Long coverLetterItemId,
        String question,
        String answer
    ) {
    }
}
