package org.myeonjeobjjang.domain.core.conversationLog.service;

import org.myeonjeobjjang.domain.core.conversationLog.entity.ConversationLog;

import java.util.List;

public interface ConversationLogService {
    void add(List<ConversationLog> stringStream);

    List<ConversationLog> get(String conversationId, int lastN);

    void clear(String conversationId);
}
