package org.myeonjeobjjang.applicant.coverLetterItem;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.CoverLetterItemService;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.IntegrationCoverLetterItemRequest;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.IntegrationCoverLetterItemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicant/coverLetterItems")
@RequiredArgsConstructor
public class CoverLetterItemApplicantController {
    private final CoverLetterItemService coverLetterItemService;

    @GetMapping("/{coverLetterItemId}")
    public ResponseEntity<IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse> get(@PathVariable Long coverLetterItemId) {
        return ResponseEntity.ok(coverLetterItemService.get(coverLetterItemId));
    }

    public record CoverLetterItemUpdateRequest(
        String question,
        String answer
    ) {}

    public record CoverLetterItemUpdateResponse(
        boolean isUpdated
    ) {}

    @PatchMapping("/{coverLetterItemId}")
    public ResponseEntity<CoverLetterItemUpdateResponse> update(@PathVariable Long coverLetterItemId,@RequestBody CoverLetterItemUpdateRequest request) {
        return ResponseEntity.ok(new CoverLetterItemUpdateResponse(coverLetterItemService.update(new IntegrationCoverLetterItemRequest.IntegrationCoverLetterItemUpdateRequest(coverLetterItemId, request.question(), request.answer()))));
    }

    @PostMapping("/coverLetters/{coverLetterId}")
    public ResponseEntity<IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse> moreQuestion(@PathVariable Long coverLetterId) {
        return ResponseEntity.ok(coverLetterItemService.createMoreQuestionByCoverLetterId(coverLetterId));
    }
}
