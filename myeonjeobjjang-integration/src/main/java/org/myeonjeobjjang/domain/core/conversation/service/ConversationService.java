package org.myeonjeobjjang.domain.core.conversation.service;

import org.myeonjeobjjang.domain.core.conversation.service.dto.ConversationResponse.ConversationCreateResponse;
import org.myeonjeobjjang.domain.core.conversationLog.service.dto.ConversationLogResponse.ConversationLogNoOffsetGetResponse;
import org.myeonjeobjjang.domain.core.member.entity.Member;

import java.time.LocalDateTime;

public interface ConversationService {
    ConversationCreateResponse create(Member member, Long coverLetterId, Long resumeId);

    String mockInterviewChat(Member member, String userMessage, Long conversationId);

    ConversationLogNoOffsetGetResponse noOffsetGetConversationLog(Long conversationId, LocalDateTime lastConversationCreatedAt, Integer amount);
}
