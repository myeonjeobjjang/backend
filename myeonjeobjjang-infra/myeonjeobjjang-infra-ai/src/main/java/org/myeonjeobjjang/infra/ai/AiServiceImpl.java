package org.myeonjeobjjang.infra.ai;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.infra.client.llm.LlmClientService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {
    private final LlmClientService llmClientService;

    @Override
    public String requestSimpleQuestion(String request) {
        return llmClientService.simpleQuestion(request);
    }
}
