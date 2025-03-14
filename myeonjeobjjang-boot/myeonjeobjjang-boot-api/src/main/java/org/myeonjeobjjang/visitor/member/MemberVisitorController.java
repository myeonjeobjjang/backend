package org.myeonjeobjjang.visitor.member;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.member.service.MemberService;
import org.myeonjeobjjang.domain.core.member.service.dto.MemberRequest;
import org.myeonjeobjjang.domain.core.member.service.dto.MemberResponse.LoginOrSignUpResponse;
import org.myeonjeobjjang.visitor.member.dto.MemberVisitorRequest.LoginVisitorRequest;
import org.myeonjeobjjang.visitor.member.dto.MemberVisitorRequest.SignUpVisitorRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/visitor/members")
@RequiredArgsConstructor
public class MemberVisitorController {
    private final MemberService memberService;

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<LoginOrSignUpResponse> login(
        @RequestBody @Validated LoginVisitorRequest request
    ) {
        return ResponseEntity.ok(memberService.login(
            new MemberRequest.LoginRequest(request.email())
        ));
    }

    @PostMapping(value = "/sign-up", consumes = "application/json")
    public ResponseEntity<LoginOrSignUpResponse> signUp(
        @RequestBody @Validated SignUpVisitorRequest request
    ) {
        return ResponseEntity.ok(memberService.signUp(
            new MemberRequest.SignUpRequest(request.email(), request.userName())
        ));
    }
}
