package org.myeonjeobjjang.infra.client.mockInterview.memory;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationLogRDBReadOnlyMemory implements ChatMemory {
    private final ConversationLogRDBMemory conversationLogRDBMemory;

    @Override
    public void add(String conversationId, List<Message> messages) {
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        return conversationLogRDBMemory.get(conversationId, lastN);
    }

    @Override
    public void clear(String conversationId) {
        conversationLogRDBMemory.clear(conversationId);
    }
}
