package org.myeonjeobjjang.infra.client.mockInterview.dto;

import org.myeonjeobjjang.domain.core.conversation.entity.Conversation;

public class MockInterviewClientRequest {
    public record MockInterviewChatRequest(
        String userMessage,
        Long industryId,
        String industryInfo,
        Long companyId,
        String companyInfo,
        Long jobDescriptionId,
        String jobDescriptionInfo,
        Long jobPostingId,
        String conversationId
    ) {
        public static MockInterviewChatRequest toDto(String userMessage, Conversation conversation, String prefix) {
            return new MockInterviewClientRequest.MockInterviewChatRequest(
                userMessage,
                conversation.getIndustry().getIndustryId(),
                conversation.getIndustry().getIndustryName() + " : " + conversation.getIndustry().getIndustryInformation(),
                conversation.getCompany().getCompanyId(),
                conversation.getCompany().getCompanyName() + " : " + conversation.getCompany().getCompanyInformation(),
                conversation.getJobDescription().getJobDescriptionId(),
                conversation.getJobDescription().getJobName() + " : " + conversation.getJobDescription().getDescription(),
                conversation.getJobPosting().getJobPostingId(),
                prefix + conversation.getConversationId()
            );
        }
    }
}
