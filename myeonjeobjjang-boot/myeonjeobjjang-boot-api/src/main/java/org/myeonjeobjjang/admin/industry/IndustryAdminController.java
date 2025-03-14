package org.myeonjeobjjang.admin.industry;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.admin.industry.dto.IndustryAdminRequest.CreateIndustryAdminRequest;
import org.myeonjeobjjang.domain.core.industry.service.IndustryService;
import org.myeonjeobjjang.domain.core.industry.service.dto.IndustryRequest.IndustryCreateRequest;
import org.myeonjeobjjang.domain.core.industry.service.dto.IndustryResponse.IndustryInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/industries")
@RequiredArgsConstructor
public class IndustryAdminController {
    private final IndustryService industryService;

    @PostMapping
    public ResponseEntity<IndustryInfoResponse> createIndustry(@RequestBody @Validated CreateIndustryAdminRequest request) {
        return ResponseEntity.ok(industryService.save(new IndustryCreateRequest(request.industryName(), request.industryInformation())));
    }
}
