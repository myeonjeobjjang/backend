package org.myeonjeobjjang.domain.core.conversation.service;

import org.myeonjeobjjang.domain.core.conversation.service.dto.ConversationResponse.ConversationCreateResponse;
import org.myeonjeobjjang.domain.core.member.entity.Member;

public interface ConversationService {
    ConversationCreateResponse create(Member member, Long coverLetterId, Long resumeId);
}
