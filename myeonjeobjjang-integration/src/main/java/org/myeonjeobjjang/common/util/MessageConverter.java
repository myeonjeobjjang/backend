package org.myeonjeobjjang.common.util;

import com.google.gson.Gson;
import org.myeonjeobjjang.domain.core.conversationLog.entity.ConversationLog;
import org.myeonjeobjjang.domain.core.conversationLog.entity.ConversationMessageType;
import org.springframework.ai.chat.messages.*;

public class MessageConverter {
    private static final Gson gson = new Gson();

    public static ConversationLog toEntity(String conversationId, Message message) {
        return ConversationLog.builder()
            .conversationId(conversationId)
            .data(
                switch (message.getMessageType()) {
                    case USER -> gson.toJson(message, UserMessage.class);
                    case SYSTEM -> gson.toJson(message, SystemMessage.class);
                    case ASSISTANT -> gson.toJson(message, AssistantMessage.class);
                    case TOOL -> gson.toJson(message, ToolResponseMessage.class);
                }
            )
            .messageType(ConversationMessageType.fromValue(message.getMessageType().getValue()))
            .build();
    }

    public static Message convertToMessage(ConversationMessageType messageType, String data) {
        return switch (messageType) {
            case USER -> gson.fromJson(data, UserMessage.class);
            case SYSTEM -> gson.fromJson(data, SystemMessage.class);
            case ASSISTANT -> gson.fromJson(data, AssistantMessage.class);
            case TOOL -> gson.fromJson(data, ToolResponseMessage.class);
        };
    }

    public static String getMessageText(ConversationMessageType messageType, String data) {
        return switch (messageType) {
            case USER -> gson.fromJson(data, UserMessage.class).getText();
            case SYSTEM -> gson.fromJson(data, SystemMessage.class).getText();
            case ASSISTANT -> gson.fromJson(data, AssistantMessage.class).getText();
            case TOOL -> gson.fromJson(data, ToolResponseMessage.class).getText();
        };
    }
}
