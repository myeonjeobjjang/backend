package org.myeonjeobjjang.domain.core.conversationLog.service.dto;

import org.myeonjeobjjang.common.util.MessageConverter;
import org.myeonjeobjjang.domain.core.conversationLog.entity.ConversationMessageType;
import org.myeonjeobjjang.domain.core.conversationLog.repository.dto.ConversationLogProjection.PassedConversationLogProjection;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDateTime;

public class ConversationLogResponse {
    public record ConversationLogInfo(
        Long conversationLogId,
        String message,
        ConversationMessageType messageType
    ) {
        public static ConversationLogInfo toDto(PassedConversationLogProjection clp) {
            return new ConversationLogInfo(
                clp.getConversationLogId(),
                MessageConverter.getMessageText(clp.getMessageType(),clp.getData()),
                clp.getMessageType());
        }
    }

    public record ConversationLogNoOffsetGetResponse(
        LocalDateTime lastConversationCreatedAt,
        Slice<ConversationLogInfo> conversationLogInfos
    ) {
        public static ConversationLogNoOffsetGetResponse toDto(LocalDateTime lastConversationCreatedAt, Slice<PassedConversationLogProjection> conversationLogProjections) {
            return new ConversationLogNoOffsetGetResponse(
                (conversationLogProjections.isEmpty() ?
                    (lastConversationCreatedAt == null ? LocalDateTime.now() : lastConversationCreatedAt)
                    : conversationLogProjections.getContent().get(0).getLastConversationCreatedAt()),
                new SliceImpl<>(
                    conversationLogProjections.stream().map(ConversationLogInfo::toDto).toList(),
                    conversationLogProjections.getPageable(),
                    conversationLogProjections.hasNext()
                )
            );
        }
    }
}
