package org.myeonjeobjjang.infra.client.llm.memoryChat;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.chat.llmchatlog.LlmChatLog;
import org.myeonjeobjjang.domain.chat.llmchatlog.LlmChatLogService;
import org.myeonjeobjjang.domain.chat.llmchatlog.LlmMessageType;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LlmChatRDBMemory implements ChatMemory {
    private final LlmChatLogService llmChatLogService;
    private final Gson gson;

    @Override
    public void add(String conversationId, List<Message> messages) {
        List<LlmChatLog> stringStream = messages.stream().map(m -> LlmChatLog.builder()
            .conversationId(conversationId)
            .data(
                switch (m.getMessageType()) {
                    case USER -> gson.toJson(m, UserMessage.class);
                    case SYSTEM -> gson.toJson(m, SystemMessage.class);
                    case ASSISTANT -> gson.toJson(m, AssistantMessage.class);
                    case TOOL -> gson.toJson(m, ToolResponseMessage.class);
                }
            )
            .messageType(LlmMessageType.fromValue(m.getMessageType().getValue()))
            .build()
        ).toList();
        llmChatLogService.add(stringStream);
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        List<LlmChatLog> response = llmChatLogService.get(conversationId, lastN);
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
        llmChatLogService.clear(conversationId);
    }
}
