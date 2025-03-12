package org.myeonjeobjjang.infra.client.openai;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.myeonjeobjjang.MyeonjeobjjangIntegrationTestSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("OpenAi 클라이언트가 ")
class CustomOpenAiClientTest extends MyeonjeobjjangIntegrationTestSupport {
    private static final Logger log = LoggerFactory.getLogger(CustomOpenAiClientTest.class);

    @Autowired
    private CustomOpenAiClient customOpenAiClient;

    @Test
    @DisplayName("간단한 질문에 답할 수 있다.")
    @Disabled
    void simpleQuestionGpt4O() {
        // given
        String message = "reply any shortest word";

        // when
        String res = customOpenAiClient.simpleQuestionGpt4O(message);

        // then
        log.info(res);
        assertNotNull(res);
    }
}