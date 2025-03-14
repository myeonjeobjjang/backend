package org.myeonjeobjjang.applicant.resume;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.applicant.resume.dto.ResumeApplicationApplicantRequest.ResumeCreateApplicantRequest;
import org.myeonjeobjjang.applicant.resume.dto.ResumeApplicationApplicantRequest.ResumeInfoApplicantRequest;
import org.myeonjeobjjang.config.security.PrincipalDetails;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.domain.core.resume.service.ResumeService;
import org.myeonjeobjjang.domain.core.resume.service.dto.ResumeResponse.ResumeInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicant/resumes")
@RequiredArgsConstructor
public class ResumeApplicantController {
    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<ResumeInfoResponse> create(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody ResumeCreateApplicantRequest request
    ) {
        Member member = principalDetails.getMember();
        return ResponseEntity.ok(resumeService.create(request.toServiceRequest(member)));
    }

    @PatchMapping("/{resumeId}")
    public ResponseEntity<ResumeInfoResponse> update(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long resumeId,
        @RequestBody ResumeInfoApplicantRequest request
    ) {
        Member member = principalDetails.getMember();
        return ResponseEntity.ok(resumeService.update(request.toServiceRequest(resumeId, member)));
    }

    @GetMapping("/{resumeId}")
    public ResponseEntity<ResumeInfoResponse> get(@PathVariable Long resumeId) {
        return ResponseEntity.ok(resumeService.get(resumeId));
    }
}
