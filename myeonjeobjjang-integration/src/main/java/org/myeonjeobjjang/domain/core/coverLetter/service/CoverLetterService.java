package org.myeonjeobjjang.domain.core.coverLetter.service;

import org.myeonjeobjjang.domain.core.coverLetter.repository.dto.CoverLetterProjection.CoverLetterInfoForConversationProjection;
import org.myeonjeobjjang.domain.core.coverLetter.service.dto.CoverLetterResponse.CoverLetterInfoResponse;
import org.myeonjeobjjang.domain.core.member.entity.Member;

public interface CoverLetterService {
    CoverLetterInfoResponse createEmpty(Member member);

    CoverLetterInfoResponse createWithJobDescription(Member member, Long jobDescriptionId);

    CoverLetterInfoResponse get(Long coverLetterId);

    int embeddingCoverLetter(Long coverLetterId, Long conversationId);

    CoverLetterInfoForConversationProjection findCoverLetterForConversation(Long coverLetterId);
}
