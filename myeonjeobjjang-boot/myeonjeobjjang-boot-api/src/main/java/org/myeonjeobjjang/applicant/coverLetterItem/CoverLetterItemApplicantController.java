package org.myeonjeobjjang.applicant.coverLetterItem;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.applicant.coverLetterItem.dto.CoverLetterItemApplicantRequest.CoverLetterItemUpdateApplicantRequest;
import org.myeonjeobjjang.applicant.coverLetterItem.dto.CoverLetterItemApplicantResponse.CoverLetterItemUpdateApplicantResponse;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.CoverLetterItemService;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.CoverLetterItemRequest.CoverLetterItemUpdateRequest;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.CoverLetterItemResponse.CoverLetterItemInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicant/coverLetterItems")
@RequiredArgsConstructor
public class CoverLetterItemApplicantController {
    private final CoverLetterItemService coverLetterItemService;

    @GetMapping("/{coverLetterItemId}")
    public ResponseEntity<CoverLetterItemInfoResponse> get(@PathVariable Long coverLetterItemId) {
        return ResponseEntity.ok(coverLetterItemService.get(coverLetterItemId));
    }

    @PatchMapping("/{coverLetterItemId}")
    public ResponseEntity<CoverLetterItemUpdateApplicantResponse> update(
        @PathVariable Long coverLetterItemId,
        @RequestBody CoverLetterItemUpdateApplicantRequest request
    ) {
        return ResponseEntity.ok(
            CoverLetterItemUpdateApplicantResponse.toDto(
                coverLetterItemService.update(
                    new CoverLetterItemUpdateRequest(coverLetterItemId, request.question(), request.answer())
                )));
    }

    @PostMapping("/coverLetters/{coverLetterId}")
    public ResponseEntity<CoverLetterItemInfoResponse> moreQuestion(@PathVariable Long coverLetterId) {
        return ResponseEntity.ok(coverLetterItemService.createMoreQuestionByCoverLetterId(coverLetterId));
    }
}
