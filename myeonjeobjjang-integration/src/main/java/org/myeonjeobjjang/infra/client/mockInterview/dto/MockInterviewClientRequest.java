package org.myeonjeobjjang.infra.client.mockInterview.dto;

import org.myeonjeobjjang.domain.core.conversation.entity.Conversation;

public class MockInterviewClientRequest {
    public record MockInterviewChatRequest(
        String userMessage,
        String industryInfo,
        String companyInfo,
        String jobDescriptionInfo,
        String conversationId
    ) {
        public static MockInterviewChatRequest toDto(String userMessage, Conversation conversation, String prefix) {
            return new MockInterviewClientRequest.MockInterviewChatRequest(
                userMessage,
                conversation.getIndustryInfo(),
                conversation.getCompanyInfo(),
                conversation.getJobDescriptionInfo(),
                prefix + conversation.getConversationId()
            );
        }
    }
}
