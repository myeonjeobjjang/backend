package org.myeonjeobjjang.domain.core.conversationLog.service;

import org.myeonjeobjjang.domain.core.conversationLog.entity.ConversationLog;
import org.myeonjeobjjang.domain.core.conversationLog.service.dto.ConversationLogResponse.ConversationLogNoOffsetGetResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ConversationLogService {
    void add(List<ConversationLog> stringStream);

    List<ConversationLog> get(String conversationId, int lastN);

    void clear(String conversationId);

    ConversationLogNoOffsetGetResponse noOffsetGet(String conversationId, LocalDateTime lastConversationCreatedAt, Integer amount);
}
