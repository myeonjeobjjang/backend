package org.myeonjeobjjang.domain.core.conversationLog.entity;

import lombok.Getter;

@Getter
public enum ConversationMessageType {
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system"),
    TOOL("tool"),
    ;
    private final String value;

    private ConversationMessageType(String value) {
        this.value = value;
    }

    public static ConversationMessageType fromValue(String value) {
        ConversationMessageType[] values = values();
        for (int i = 0; i < values.length; ++i) {
            ConversationMessageType messageType = values[i];
            if (messageType.getValue().equals(value)) {
                return messageType;
            }
        }

        throw new IllegalArgumentException("Invalid ConversationMessageType value: " + value);
    }
}
