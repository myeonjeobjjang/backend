package org.myeonjeobjjang.applicant.member;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.applicant.member.dto.MemberApplicantResponse.MemberInfoApplicantResponse;
import org.myeonjeobjjang.config.security.PrincipalDetails;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.domain.core.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applicant/members")
@RequiredArgsConstructor
public class MemberApplicantController {
    private final MemberService memberService;

    @GetMapping("/who")
    public ResponseEntity<MemberInfoApplicantResponse> whoIsMe(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        return ResponseEntity.ok(new MemberInfoApplicantResponse(member.getMemberId(), member.getUserName(), member.getEmail(), member.getRole()));
    }
}
