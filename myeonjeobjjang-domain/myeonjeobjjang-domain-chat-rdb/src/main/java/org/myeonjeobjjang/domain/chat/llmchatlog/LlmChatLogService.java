package org.myeonjeobjjang.domain.chat.llmchatlog;

import java.util.List;

public interface LlmChatLogService {
    void add(List<LlmChatLog> stringStream);

    List<LlmChatLog> get(String conversationId, int lastN);

    void clear(String conversationId);
}
