package org.myeonjeobjjang.infra.client.llm;

import lombok.RequiredArgsConstructor;
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
}
