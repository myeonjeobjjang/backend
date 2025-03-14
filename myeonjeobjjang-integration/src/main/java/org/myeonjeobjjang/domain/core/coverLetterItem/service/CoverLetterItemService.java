package org.myeonjeobjjang.domain.core.coverLetterItem.service;

import org.myeonjeobjjang.domain.core.coverLetter.entity.CoverLetter;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.CoverLetterItemRequest.CoverLetterItemCopyFromJobDescriptionRequest;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.CoverLetterItemRequest.CoverLetterItemUpdateRequest;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.CoverLetterItemResponse.CoverLetterItemInfoResponse;

import java.util.List;

public interface CoverLetterItemService {
    CoverLetterItemInfoResponse createMoreQuestionByCoverLetterId(Long coverLetterId);

    CoverLetterItemInfoResponse createMoreQuestion(CoverLetter coverLetter);

    List<CoverLetterItemInfoResponse> copyQuestionFromJobDescription(CoverLetterItemCopyFromJobDescriptionRequest request);

    CoverLetterItemInfoResponse get(Long coverLetterItemId);

    boolean update(CoverLetterItemUpdateRequest request);
}
