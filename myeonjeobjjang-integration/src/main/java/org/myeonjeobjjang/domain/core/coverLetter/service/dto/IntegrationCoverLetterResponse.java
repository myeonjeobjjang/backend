package org.myeonjeobjjang.domain.core.coverLetter.service.dto;

import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.IntegrationCoverLetterItemResponse;

import java.util.List;

public class IntegrationCoverLetterResponse {
    public record IntegrationCoverLetterInfoResponse(
        Long coverLetterId,
        String jobName,
        String description,
        List<IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse> coverLetterItemInfos
    ) {}
}
