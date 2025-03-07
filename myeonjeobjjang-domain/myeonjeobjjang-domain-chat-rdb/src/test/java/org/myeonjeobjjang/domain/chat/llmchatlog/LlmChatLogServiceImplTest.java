package org.myeonjeobjjang.domain.chat.llmchatlog;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.myeonjeobjjang.domain.chat.LlmChatLogTestSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DisplayName("LlmChatLogService로 ")
class LlmChatLogServiceImplTest extends LlmChatLogTestSupport {
    @Autowired
    private LlmChatLogRepository llmChatLogRepository;
    @Autowired
    private LlmChatLogServiceImpl llmChatLogService;

    @AfterEach
    void tearDown() {
        llmChatLogRepository.deleteAllInBatch();
    }

    @DisplayName("새로운 대화를 넣을 수 있다.")
    @Test
    void add() {
        // given
        String conversationId1 = "conversation1";
        LlmChatLog llmChatLog1 = LlmChatLog.builder().conversationId(conversationId1).messageType(LlmMessageType.USER).data("안녕!").build();
        LlmChatLog llmChatLog2 = LlmChatLog.builder().conversationId(conversationId1).messageType(LlmMessageType.SYSTEM).data("안녕하세요! 무엇을 도와드릴까요?").build();

        // when
        llmChatLogService.add(List.of(llmChatLog1, llmChatLog2));

        // then
        List<LlmChatLog> all = llmChatLogRepository.findAll();
        assertThat(all).hasSize(2)
            .extracting("conversationId", "messageType", "data")
            .containsExactlyInAnyOrder(
                tuple("conversation1", LlmMessageType.USER, "안녕!"),
                tuple("conversation1", LlmMessageType.SYSTEM, "안녕하세요! 무엇을 도와드릴까요?")
            );
    }

    @DisplayName("최근의 lastN개의 대화를 가져올 수 있다.")
    @Test
    void get() {
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
        List<LlmChatLog> llmChatLogs = llmChatLogService.get(conversationId1, lastN);

        // then
        assertThat(llmChatLogs).hasSize(lastN)
            .extracting("conversationId", "messageType", "data")
            .containsExactlyInAnyOrder(
                AssertionsForClassTypes.tuple(conversationId1, LlmMessageType.SYSTEM, llmChatLog5.getData()),
                AssertionsForClassTypes.tuple(conversationId1, LlmMessageType.USER, llmChatLog3.getData())
            );
    }

    @DisplayName("이전의 대화가 없는 경우 비어있는 리스트를 가져온다.")
    @Test
    void getNone() {
        // given
        String conversationId1 = "conversation1";
        String conversationId2 = "conversation2";
        LlmChatLog llmChatLog4 = LlmChatLog.builder().conversationId(conversationId2).messageType(LlmMessageType.USER).data("안녕! 망고라고 해! 넌 누구야?").build();
        LlmChatLog llmChatLog6 = LlmChatLog.builder().conversationId(conversationId2).messageType(LlmMessageType.SYSTEM).data("안녕! 너는 망고구나. 나는 인공지능 챗봇이야.").build();
        int lastN = 2;

        llmChatLogRepository.saveAll(List.of(
            llmChatLog4, llmChatLog6
        ));

        // when
        List<LlmChatLog> llmChatLogs = llmChatLogService.get(conversationId1, lastN);

        // then
        assertThat(llmChatLogs).hasSize(0)
            .extracting("conversationId", "messageType", "data")
            .isEmpty();
    }

    @DisplayName("특정 conversationId의 대화기록을 모두 제거할 수 있다.")
    @Test
    void clear() {
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
        llmChatLogService.clear(conversationId1);

        // then
        List<LlmChatLog> all = llmChatLogRepository.findAll();
        assertThat(all)
            .extracting("conversationId")
            .doesNotContain(conversationId1);
    }
}