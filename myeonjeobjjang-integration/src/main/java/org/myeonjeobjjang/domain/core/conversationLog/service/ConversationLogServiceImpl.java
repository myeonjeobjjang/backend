package org.myeonjeobjjang.domain.core.conversationLog.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.conversationLog.entity.ConversationLog;
import org.myeonjeobjjang.domain.core.conversationLog.repository.ConversationLogRepository;
import org.springframework.stereotype.Service;

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
}
