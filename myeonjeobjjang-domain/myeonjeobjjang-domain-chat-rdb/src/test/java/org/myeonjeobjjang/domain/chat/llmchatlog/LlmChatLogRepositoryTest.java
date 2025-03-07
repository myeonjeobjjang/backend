package org.myeonjeobjjang.domain.chat.llmchatlog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.myeonjeobjjang.domain.chat.LlmChatLogTestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@DisplayName("LlmChatLogRepository로 ")
@Transactional
class LlmChatLogRepositoryTest extends LlmChatLogTestSupport {
    @Autowired
    private LlmChatLogRepository llmChatLogRepository;

    @DisplayName("conversationId의 최근 lastN개의 기록을 가져온다.")
    @Test
    void findAllByConversationId() {
        // given
        String conversationId1 = "conversation1";
        String conversationId2 = "conversation2";
        LlmChatLog llmChatLog1 = LlmChatLog.builder().conversationId(conversationId1).messageType(LlmMessageType.USER).data("안녕!").build();
        LlmChatLog llmChatLog2 = LlmChatLog.builder().conversationId(conversationId1).messageType(LlmMessageType.SYSTEM).data("안녕하세요! 무엇을 도와드릴까요?").build();
        LlmChatLog llmChatLog3 = LlmChatLog.builder().conversationId(conversationId1).messageType(LlmMessageType.USER).data("점심 메뉴 추천해줘").build();
        LlmChatLog llmChatLog4 = LlmChatLog.builder().conversationId(conversationId2).messageType(LlmMessageType.USER).data("안녕! 망고라고 해! 넌 누구야?").build();
        LlmChatLog llmChatLog5 = LlmChatLog.builder().conversationId(conversationId1).messageType(LlmMessageType.SYSTEM).data("점심 메뉴를 추천해줄게! 어느나라 음식을 먹고싶어?").build();
        LlmChatLog llmChatLog6 = LlmChatLog.builder().conversationId(conversationId2).messageType(LlmMessageType.SYSTEM).data("안녕! 너는 망고구나. 나는 인공지능 챗봇이야.").build();
        int lastN = 2;

        llmChatLogRepository.saveAll(List.of(
            llmChatLog1, llmChatLog2, llmChatLog3, llmChatLog4, llmChatLog5, llmChatLog6
        ));

        // when
        List<LlmChatLog> allByConversationId = llmChatLogRepository.findAllByConversationId(conversationId1, lastN);

        // then
        assertThat(allByConversationId).hasSize(lastN)
            .extracting("conversationId", "messageType", "data")
            .containsExactlyInAnyOrder(
                tuple(conversationId1, LlmMessageType.SYSTEM, llmChatLog5.getData()),
                tuple(conversationId1, LlmMessageType.USER, llmChatLog3.getData())
            );
    }

    @DisplayName("conversationId의 기록을 모두 제거할 수 있다.")
    @Test
    void deleteAllByConversationId() {
        // given
        String conversationId1 = "conversation1";
        String conversationId2 = "conversation2";
        LlmChatLog llmChatLog1 = LlmChatLog.builder().conversationId(conversationId1).messageType(LlmMessageType.USER).data("안녕!").build();
        LlmChatLog llmChatLog2 = LlmChatLog.builder().conversationId(conversationId1).messageType(LlmMessageType.SYSTEM).data("안녕하세요! 무엇을 도와드릴까요?").build();
        LlmChatLog llmChatLog3 = LlmChatLog.builder().conversationId(conversationId1).messageType(LlmMessageType.USER).data("점심 메뉴 추천해줘").build();
        LlmChatLog llmChatLog4 = LlmChatLog.builder().conversationId(conversationId2).messageType(LlmMessageType.USER).data("안녕! 망고라고 해! 넌 누구야?").build();
        LlmChatLog llmChatLog5 = LlmChatLog.builder().conversationId(conversationId1).messageType(LlmMessageType.SYSTEM).data("점심 메뉴를 추천해줄게! 어느나라 음식을 먹고싶어?").build();
        LlmChatLog llmChatLog6 = LlmChatLog.builder().conversationId(conversationId2).messageType(LlmMessageType.SYSTEM).data("안녕! 너는 망고구나. 나는 인공지능 챗봇이야.").build();

        llmChatLogRepository.saveAll(List.of(
            llmChatLog1, llmChatLog2, llmChatLog3, llmChatLog4, llmChatLog5, llmChatLog6
        ));

        // when
        llmChatLogRepository.deleteAllByConversationId(conversationId1);

        // then
        List<LlmChatLog> all = llmChatLogRepository.findAll();
        assertThat(all)
            .extracting("conversationId")
            .doesNotContain(conversationId1);
    }
}