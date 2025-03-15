package org.myeonjeobjjang.infra.client.mockInterview;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.conversationLog.entity.ConversationLog;
import org.myeonjeobjjang.domain.core.conversationLog.entity.ConversationMessageType;
import org.myeonjeobjjang.domain.core.conversationLog.service.ConversationLogService;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationLogRDBMemory implements ChatMemory {
    private final ConversationLogService conversationLogService;
    private final Gson gson;

    @Override
    public void add(String conversationId, List<Message> messages) {
        List<ConversationLog> stringStream = messages.stream().map(m -> ConversationLog.builder()
            .conversationId(conversationId)
            .data(
                switch (m.getMessageType()) {
                    case USER -> gson.toJson(m, UserMessage.class);
                    case SYSTEM -> gson.toJson(m, SystemMessage.class);
                    case ASSISTANT -> gson.toJson(m, AssistantMessage.class);
                    case TOOL -> gson.toJson(m, ToolResponseMessage.class);
                }
            )
            .messageType(ConversationMessageType.fromValue(m.getMessageType().getValue()))
            .build()
        ).toList();
        conversationLogService.add(stringStream);
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        List<ConversationLog> response = conversationLogService.get(conversationId, lastN);
        return response.stream().map(llmChatLog -> (Message) switch (llmChatLog.getMessageType()) {
                case USER -> gson.fromJson(llmChatLog.getData(), UserMessage.class);
                case SYSTEM -> gson.fromJson(llmChatLog.getData(), SystemMessage.class);
                case ASSISTANT -> gson.fromJson(llmChatLog.getData(), AssistantMessage.class);
                case TOOL -> gson.fromJson(llmChatLog.getData(), ToolResponseMessage.class);
            }
        ).toList();
    }

    @Override
    public void clear(String conversationId) {
        conversationLogService.clear(conversationId);
    }
}
