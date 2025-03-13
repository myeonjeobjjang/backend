package org.myeonjeobjjang.domain.core.coverLetterItem.service.dto;

public class IntegrationCoverLetterItemResponse {
    public record IntegrationCoverLetterItemInfoResponse(
        Long coverLetterItemId,
        Long questionNumber,
        String question,
        String answer,
        Long coverLetterId
    ) {}
}
