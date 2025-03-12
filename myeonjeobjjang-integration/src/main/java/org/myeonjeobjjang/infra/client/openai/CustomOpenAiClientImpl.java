package org.myeonjeobjjang.infra.client.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class CustomOpenAiClientImpl implements CustomOpenAiClient {
    private static final Logger log = LoggerFactory.getLogger(CustomOpenAiClientImpl.class);
    private final OpenAiChatModel openAiChatModel;
    private final ObjectMapper objectMapper;

    @Override
    public String simpleQuestionGpt4O(String question) {
        return ChatClient.builder(openAiChatModel).build()
            .prompt(new Prompt("", OpenAiChatOptions.builder().model(OpenAiApi.ChatModel.GPT_4_O).build()))
            .user(question)
            .call().content();
    }

    @Override
    public <T> T getGpt4O(String systemMessage, String userMessage, Class<T> responseType) throws JsonProcessingException {
        String res = ChatClient.builder(openAiChatModel).build()
            .prompt(new Prompt("", OpenAiChatOptions.builder().model(OpenAiApi.ChatModel.GPT_4_O).build()))
            .system(systemMessage)
            .user(userMessage)
            .call().content();
        T response = objectMapper.readValue(res, responseType);
        log.debug(response.toString());
        return response;
    }

    @Override
    public Flux<String> memoryStreamChat(String message, MessageChatMemoryAdvisor advisor) {
        return ChatClient.builder(openAiChatModel).build()
            .prompt()
            .advisors(advisor)
            .user(message)
            .stream()
            .content();
    }
}
