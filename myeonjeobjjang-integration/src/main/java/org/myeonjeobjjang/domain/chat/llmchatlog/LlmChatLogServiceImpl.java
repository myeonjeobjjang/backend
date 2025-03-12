package org.myeonjeobjjang.domain.chat.llmchatlog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LlmChatLogServiceImpl implements LlmChatLogService {
    private final LlmChatLogRepository llmChatLogRepository;

    @Override
    public void add(List<LlmChatLog> stringStream) {
        llmChatLogRepository.saveAll(stringStream);
    }

    @Override
    public List<LlmChatLog> get(String conversationId, int lastN) {
        return llmChatLogRepository.findAllByConversationId(conversationId, lastN);
    }

    @Transactional
    @Override
    public void clear(String conversationId) {
        llmChatLogRepository.deleteAllByConversationId(conversationId);
    }
}
