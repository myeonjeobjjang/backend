package org.myeonjeobjjang.applicant.industry;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.industry.service.IndustryService;
import org.myeonjeobjjang.domain.core.industry.service.dto.IndustryResponse.IndustryInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applicant/industries")
@RequiredArgsConstructor
public class IndustryApplicantController {
    private final IndustryService industryService;

    @GetMapping("/{industryId}")
    public ResponseEntity<IndustryInfoResponse> getIndustry(@PathVariable Long industryId) {
        return ResponseEntity.ok(industryService.get(industryId));
    }
}
