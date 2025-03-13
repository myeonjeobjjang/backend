package org.myeonjeobjjang.domain.core.coverLetter.service;

import org.myeonjeobjjang.domain.core.coverLetter.service.dto.IntegrationCoverLetterResponse;
import org.myeonjeobjjang.domain.core.member.entity.Member;

public interface CoverLetterService {
    IntegrationCoverLetterResponse.IntegrationCoverLetterInfoResponse createEmpty(Member member);
    IntegrationCoverLetterResponse.IntegrationCoverLetterInfoResponse createWithJobDescription(Member member, Long jobDescriptionId);
    IntegrationCoverLetterResponse.IntegrationCoverLetterInfoResponse get(Long coverLetterId);
}
