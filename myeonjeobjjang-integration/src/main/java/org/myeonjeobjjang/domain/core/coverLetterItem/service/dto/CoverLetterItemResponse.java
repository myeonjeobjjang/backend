package org.myeonjeobjjang.domain.core.coverLetterItem.service.dto;

import org.myeonjeobjjang.domain.core.coverLetter.repository.dto.CoverLetterProjection;
import org.myeonjeobjjang.domain.core.coverLetterItem.entity.CoverLetterItem;

public class CoverLetterItemResponse {
    public record CoverLetterItemInfoResponse(
        Long coverLetterItemId,
        Long questionNumber,
        String question,
        String answer,
        Long coverLetterId
    ) {
        public static CoverLetterItemInfoResponse toDto(CoverLetterProjection.CoverLetterInfoProjection clip) {
            return new CoverLetterItemInfoResponse(
                clip.getCoverLetterItemId(),
                clip.getQuestionNumber(),
                clip.getQuestion(),
                clip.getAnswer(),
                clip.getCoverLetterId()
            );
        }

        public static CoverLetterItemInfoResponse toDto(CoverLetterItem coverLetterItem) {
            return new CoverLetterItemInfoResponse(
                coverLetterItem.getCoverLetterItemId(),
                coverLetterItem.getQuestionNumber(),
                coverLetterItem.getQuestion(),
                coverLetterItem.getAnswer(),
                coverLetterItem.getCoverLetter().getCoverLetterId()
            );
        }
    }
}
