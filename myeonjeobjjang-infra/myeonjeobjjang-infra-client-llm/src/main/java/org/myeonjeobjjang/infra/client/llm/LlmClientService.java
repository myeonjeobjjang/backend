package org.myeonjeobjjang.infra.client.llm;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.myeonjeobjjang.infra.client.llm.dto.InfraClientLlmRequest;

public interface LlmClientService {
    String simpleQuestion(String question);
    <T> T getPromptedLlmResponse(InfraClientLlmRequest.PromptedLlmRequest<T> request) throws JsonProcessingException;
}
