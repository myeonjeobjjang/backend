package org.myeonjeobjjang.applicant.conversation.dto;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class ConversationApplicantResponse {
    public record ConversationLogsApplicantResponse(
        @PastOrPresent
        LocalDateTime lastConversationCreatedAt,
        @Positive
        Integer amount
    ) {}
}
