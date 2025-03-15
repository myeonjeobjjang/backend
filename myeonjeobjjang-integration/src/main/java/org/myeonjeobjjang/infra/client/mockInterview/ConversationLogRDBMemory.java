package org.myeonjeobjjang.infra.client.mockInterview;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.common.util.MessageConverter;
import org.myeonjeobjjang.domain.core.conversationLog.entity.ConversationLog;
import org.myeonjeobjjang.domain.core.conversationLog.service.ConversationLogService;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationLogRDBMemory implements ChatMemory {
    private final ConversationLogService conversationLogService;

    @Override
    public void add(String conversationId, List<Message> messages) {
        List<ConversationLog> stringStream = messages.stream()
            .map(m -> MessageConverter.toEntity(conversationId, m)).toList();
        conversationLogService.add(stringStream);
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        List<ConversationLog> response = conversationLogService.get(conversationId, lastN);
        return response.stream().map(conversationLog ->
            MessageConverter.convertToMessage(conversationLog.getMessageType(), conversationLog.getData())).toList();
    }

    @Override
    public void clear(String conversationId) {
        conversationLogService.clear(conversationId);
    }
}
