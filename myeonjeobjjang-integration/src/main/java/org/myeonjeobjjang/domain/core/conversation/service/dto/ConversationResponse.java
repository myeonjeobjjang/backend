package org.myeonjeobjjang.domain.core.conversation.service.dto;

public class ConversationResponse {
    public record ConversationCreateResponse(
        Long conversationId,
        Integer embeddedCoverLetterQuestionCnt,
        Integer embeddedResumeItemCnt
    ) {
        public static ConversationCreateResponse toDto(
            Long conversationId,
            Integer embeddedCoverLetterQuestionCnt,
            Integer embeddedResumeItemCnt
        ) {
            return new ConversationCreateResponse(conversationId, embeddedCoverLetterQuestionCnt, embeddedResumeItemCnt);
        }
    }
}
