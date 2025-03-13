package org.myeonjeobjjang.domain.core.coverLetterItem.service;

import org.myeonjeobjjang.domain.core.coverLetter.entity.CoverLetter;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.IntegrationCoverLetterItemRequest;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.IntegrationCoverLetterItemResponse;

import java.util.List;

public interface CoverLetterItemService {
    IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse createMoreQuestionByCoverLetterId(Long coverLetterId);
    IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse createMoreQuestion(CoverLetter coverLetter);
    List<IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse> copyQuestionFromJobDescription(IntegrationCoverLetterItemRequest.IntegrationCoverLetterItemCopyFromJobDescriptionRequest request);
    IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse get(Long coverLetterItemId);
    boolean update(IntegrationCoverLetterItemRequest.IntegrationCoverLetterItemUpdateRequest request);
}
