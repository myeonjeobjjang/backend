package org.myeonjeobjjang.domain.chat.llmchatlog;

import lombok.Getter;

@Getter
public enum LlmMessageType {
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system"),
    TOOL("tool"),
    ;
    private final String value;

    private LlmMessageType(String value) {
        this.value = value;
    }

    public static LlmMessageType fromValue(String value) {
        LlmMessageType[] values = values();
        for (int i = 0; i < values.length; ++i) {
            LlmMessageType messageType = values[i];
            if (messageType.getValue().equals(value)) {
                return messageType;
            }
        }

        throw new IllegalArgumentException("Invalid LlmMessageType value: " + value);
    }
}
