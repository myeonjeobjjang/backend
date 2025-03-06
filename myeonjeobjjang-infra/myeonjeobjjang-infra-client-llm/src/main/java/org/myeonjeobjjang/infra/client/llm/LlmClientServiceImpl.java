package org.myeonjeobjjang.infra.client.llm;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.infra.client.llm.dto.InfraClientLlmRequest;
import org.myeonjeobjjang.infra.client.openai.CustomOpenAiClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LlmClientServiceImpl implements LlmClientService {
    private final CustomOpenAiClient customOpenAiClient;

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
}
