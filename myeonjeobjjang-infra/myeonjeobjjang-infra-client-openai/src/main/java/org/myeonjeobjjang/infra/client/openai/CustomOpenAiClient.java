package org.myeonjeobjjang.infra.client.openai;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CustomOpenAiClient {
    String simpleQuestionGpt4O(String question);
    <T> T getGpt4O(String systemMessage, String userMessage, Class<T> responseType) throws JsonProcessingException;
}
