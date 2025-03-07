package org.myeonjeobjjang.infra.client.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import reactor.core.publisher.Flux;

public interface CustomOpenAiClient {
    String simpleQuestionGpt4O(String question);
    <T> T getGpt4O(String systemMessage, String userMessage, Class<T> responseType) throws JsonProcessingException;
    Flux<String> memoryStreamChat(String message, MessageChatMemoryAdvisor advisor);
}
