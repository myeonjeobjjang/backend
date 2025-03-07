package org.myeonjeobjjang.infra.client.llm;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.infra.client.llm.dto.InfraClientLlmRequest;
import org.myeonjeobjjang.infra.client.llm.memoryChat.LlmChatRDBMemory;
import org.myeonjeobjjang.infra.client.openai.CustomOpenAiClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class LlmClientServiceImpl implements LlmClientService {
    private final CustomOpenAiClient customOpenAiClient;
    private final LlmChatRDBMemory rdbChatMemory;
    private final int CHAT_HISTORY_WINDOW_SIZE = 100;

    @Override
    public String simpleQuestion(String question) {
        return customOpenAiClient.simpleQuestionGpt4O(question);
    }

    @Override
    public <T> T getPromptedLlmResponse(InfraClientLlmRequest.PromptedLlmRequest<T> request) throws JsonProcessingException {
        // openAi
        return customOpenAiClient.getGpt4O(
            request.systemPromptMessage().message,
            request.userPromptMessage().message.formatted(String.join("\n", request.retrievedStrings()), request.userQuestion()),
            request.responseType()
        );
    }

    @Override
    public Flux<String> memoryStreamChat(String message, String conversationId) {
        return customOpenAiClient.memoryStreamChat(message, new MessageChatMemoryAdvisor(rdbChatMemory, conversationId, CHAT_HISTORY_WINDOW_SIZE));
    }
}
