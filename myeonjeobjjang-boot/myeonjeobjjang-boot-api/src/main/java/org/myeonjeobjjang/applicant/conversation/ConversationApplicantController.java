package org.myeonjeobjjang.applicant.conversation;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.config.security.PrincipalDetails;
import org.myeonjeobjjang.domain.core.conversation.service.ConversationService;
import org.myeonjeobjjang.domain.core.conversation.service.dto.ConversationResponse.ConversationCreateResponse;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
