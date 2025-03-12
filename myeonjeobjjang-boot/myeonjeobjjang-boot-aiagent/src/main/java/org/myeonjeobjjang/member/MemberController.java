package org.myeonjeobjjang.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.member.service.MemberService;
import org.myeonjeobjjang.domain.core.member.service.dto.IntegrationMemberRequest;
import org.myeonjeobjjang.domain.core.member.service.dto.IntegrationMemberResponse;
import org.myeonjeobjjang.member.dto.MemberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<IntegrationMemberResponse.LoginOrSignUpResponse> login(@RequestBody @Validated MemberRequest.LoginRequest request) {
        return ResponseEntity.ok(memberService.login(new IntegrationMemberRequest.IntegrationLoginRequest(request.email())));
    }


    @PostMapping(value = "/sign-up", consumes = "application/json")
    public ResponseEntity<IntegrationMemberResponse.LoginOrSignUpResponse> signUp(@RequestBody @Validated MemberRequest.SignUpRequest request) {
        return ResponseEntity.ok(memberService.signUp(new IntegrationMemberRequest.IntegrationSignUpRequest(request.email(), request.userName())));
    }
}
