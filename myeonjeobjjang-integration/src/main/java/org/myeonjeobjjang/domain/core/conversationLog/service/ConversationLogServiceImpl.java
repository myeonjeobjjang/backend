package org.myeonjeobjjang.domain.core.conversationLog.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.conversationLog.entity.ConversationLog;
import org.myeonjeobjjang.domain.core.conversationLog.repository.ConversationLogRepository;
import org.myeonjeobjjang.domain.core.conversationLog.repository.dto.ConversationLogProjection.PassedConversationLogProjection;
import org.myeonjeobjjang.domain.core.conversationLog.service.dto.ConversationLogResponse.ConversationLogNoOffsetGetResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationLogServiceImpl implements ConversationLogService {
    private final ConversationLogRepository conversationLogRepository;

    @Override
    public void add(List<ConversationLog> stringStream) {
        conversationLogRepository.saveAll(stringStream);
    }

    @Override
    public List<ConversationLog> get(String conversationId, int lastN) {
        return conversationLogRepository.findAllByConversationId(conversationId, lastN);
    }

    @Override
    public void clear(String conversationId) {
        conversationLogRepository.deleteAllByConversationId(conversationId);
    }

    @Override
    public ConversationLogNoOffsetGetResponse noOffsetGet(String conversationId, LocalDateTime lastConversationCreatedAt, Integer amount) {
        List<PassedConversationLogProjection> conversationLogProjections = conversationLogRepository.getPassedConversationLog(
            conversationId,
            lastConversationCreatedAt,
            amount + 1
        );

        boolean hasNext = conversationLogProjections.size() > amount;
        SliceImpl<PassedConversationLogProjection> conversationLogProjectionSlice = new SliceImpl<>(
            conversationLogProjections.subList(0, Math.min(amount, conversationLogProjections.size())),
            PageRequest.of(0, amount, Sort.Direction.DESC, "createdAt"),
            hasNext
        );

        return ConversationLogNoOffsetGetResponse.toDto(lastConversationCreatedAt, conversationLogProjectionSlice);
    }
}
