package org.myeonjeobjjang.applicant.coverLetter;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.config.security.PrincipalDetails;
import org.myeonjeobjjang.domain.core.coverLetter.service.CoverLetterService;
import org.myeonjeobjjang.domain.core.coverLetter.service.dto.CoverLetterResponse.CoverLetterInfoResponse;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicant/coverLetters")
@RequiredArgsConstructor
public class CoverLetterApplicantController {
    private final CoverLetterService coverLetterService;

    @PostMapping("/empty")
    public ResponseEntity<CoverLetterInfoResponse> createEmpty(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        return ResponseEntity.ok(coverLetterService.createEmpty(member));
    }

    @PostMapping("/jobDescriptions/{jobDescriptionId}")
    public ResponseEntity<CoverLetterInfoResponse> createCopy(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long jobDescriptionId) {
        Member member = principalDetails.getMember();
        return ResponseEntity.ok(coverLetterService.createWithJobDescription(member, jobDescriptionId));
    }

    @GetMapping("/{coverLetterId}")
    public ResponseEntity<CoverLetterInfoResponse> get(@PathVariable Long coverLetterId) {
        return ResponseEntity.ok(coverLetterService.get(coverLetterId));
    }
}
