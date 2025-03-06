package org.myeonjeobjjang.infra.client.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOpenAiClientImpl implements CustomOpenAiClient {
    private static final Logger log = LoggerFactory.getLogger(CustomOpenAiClientImpl.class);
    private final OpenAiChatModel openAiChatModel;

    @Override
    public String simpleQuestionGpt4O(String question) {
        return ChatClient.builder(openAiChatModel).build()
            .prompt(new Prompt("", OpenAiChatOptions.builder().model(OpenAiApi.ChatModel.GPT_4_O).build()))
            .user(question)
            .call().content();
    }
}
