package org.myeonjeobjjang.domain.core.conversationLog.repository.dto;

import org.myeonjeobjjang.domain.core.conversationLog.entity.ConversationMessageType;

import java.time.LocalDateTime;

public class ConversationLogProjection {
    public interface PassedConversationLogProjection {
        Long getConversationLogId();

        String getData();

        ConversationMessageType getMessageType();

        LocalDateTime getLastConversationCreatedAt();
    }
}
