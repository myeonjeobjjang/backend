package org.myeonjeobjjang.domain.chat.llmchatlog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LlmMessageType enum이 ")
class LlmMessageTypeTest {

    static Stream<Arguments> fromValueParameter() {
        return Stream.of(
            Arguments.of(LlmMessageType.USER, "user"),
            Arguments.of(LlmMessageType.ASSISTANT, "assistant"),
            Arguments.of(LlmMessageType.SYSTEM, "system"),
            Arguments.of(LlmMessageType.TOOL, "tool")
        );
    }

    @DisplayName("value를 넣을 때 적합한 enum을 정상적으로 반환한다.")
    @ParameterizedTest(name = "{1}을 넣을때 {0}을 반환한다.")
    @MethodSource("fromValueParameter")
    void fromValue(LlmMessageType expected, String input) {
        // given // when
        LlmMessageType messageType = LlmMessageType.fromValue(input);

        // then
        assertThat(messageType).isEqualTo(expected);
    }
}