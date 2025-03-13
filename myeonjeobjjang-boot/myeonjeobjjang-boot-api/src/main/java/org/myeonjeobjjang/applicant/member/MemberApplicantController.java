package org.myeonjeobjjang.applicant.member;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.visitor.member.dto.MemberResponse;
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
    public ResponseEntity<MemberResponse.MemberInfo> whoIsMe(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        return ResponseEntity.ok(new MemberResponse.MemberInfo(member.getMemberId(), member.getUserName(), member.getEmail(), member.getRole()));
    }
}
