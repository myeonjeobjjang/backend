package org.myeonjeobjjang.applicant.conversation.dto;

import jakarta.validation.constraints.NotEmpty;

public class ConversationApplicantRequest {
    public record MockInterviewChatApplicantRequest(
        @NotEmpty
        String userMessage
    ) {}
}
