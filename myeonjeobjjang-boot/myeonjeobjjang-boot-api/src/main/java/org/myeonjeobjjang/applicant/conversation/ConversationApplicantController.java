package org.myeonjeobjjang.applicant.conversation;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.applicant.conversation.dto.ConversationApplicantRequest.MockInterviewChatApplicantRequest;
import org.myeonjeobjjang.applicant.conversation.dto.ConversationApplicantResponse.ConversationLogsApplicantResponse;
import org.myeonjeobjjang.config.security.PrincipalDetails;
import org.myeonjeobjjang.domain.core.conversation.service.ConversationService;
import org.myeonjeobjjang.domain.core.conversation.service.dto.ConversationResponse.ConversationCreateResponse;
import org.myeonjeobjjang.domain.core.conversationLog.service.dto.ConversationLogResponse.ConversationLogNoOffsetGetResponse;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicant/conversations")
@RequiredArgsConstructor
public class ConversationApplicantController {
    private final ConversationService conversationService;

    @PostMapping("/coverLetters/{coverLetterId}/resumes/{resumeId}")
    public ResponseEntity<ConversationCreateResponse> create(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long coverLetterId,
        @PathVariable Long resumeId
    ) {
        Member member = principalDetails.getMember();
        return ResponseEntity.ok(conversationService.create(member, coverLetterId, resumeId));
    }

    @PostMapping("/{conversationId}")
    public String mockInterviewChat(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody @Validated MockInterviewChatApplicantRequest request,
        @PathVariable Long conversationId
    ) {
        Member member = principalDetails.getMember();
        return conversationService.mockInterviewChat(member, request.userMessage(), conversationId);
    }

    @PostMapping("/{conversationId}/conversationLogs")
    public ResponseEntity<ConversationLogNoOffsetGetResponse> noOffsetGetConversationLog(
        @PathVariable Long conversationId,
        @RequestBody @Validated ConversationLogsApplicantResponse request
    ) {
        return ResponseEntity.ok(conversationService.noOffsetGetConversationLog(conversationId, request.lastConversationCreatedAt(), request.amount()));
    }
}
