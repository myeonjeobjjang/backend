package org.myeonjeobjjang.infra.client.llm.dto;

import lombok.Builder;
import org.myeonjeobjjang.infra.client.llm.PromptMessage;

import java.util.List;

public class InfraClientLlmRequest {
    @Builder
    public record PromptedLlmRequest<T>(
        List<String> retrievedStrings,
        String userQuestion,
        PromptMessage systemPromptMessage,
        PromptMessage userPromptMessage,
        Class<T> responseType
    ) {}
}
