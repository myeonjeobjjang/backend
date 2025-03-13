package org.myeonjeobjjang.applicant.resume;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.applicant.resume.dto.ResumeApplicationRequest.ResumeCreateRequest;
import org.myeonjeobjjang.applicant.resume.dto.ResumeApplicationRequest.ResumeInfoRequest;
import org.myeonjeobjjang.config.security.PrincipalDetails;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.domain.core.resume.service.ResumeService;
import org.myeonjeobjjang.domain.core.resume.service.dto.IntegrationResumeResponse.IntegrationResumeInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicant/resumes")
@RequiredArgsConstructor
public class ResumeApplicantController {
    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<IntegrationResumeInfoResponse> create(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody ResumeCreateRequest request
    ) {
        Member member = principalDetails.getMember();
        return ResponseEntity.ok(resumeService.create(request.toIntegrationRequest(member)));
    }

    @PatchMapping("/{resumeId}")
    public ResponseEntity<IntegrationResumeInfoResponse> update(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long resumeId,
        @RequestBody ResumeInfoRequest request
    ) {
        Member member = principalDetails.getMember();
        return ResponseEntity.ok(resumeService.update(request.toIntegrationRequest(resumeId, member)));
    }

    @GetMapping("/{resumeId}")
    public ResponseEntity<IntegrationResumeInfoResponse> get(@PathVariable Long resumeId) {
        return ResponseEntity.ok(resumeService.get(resumeId));
    }
}
