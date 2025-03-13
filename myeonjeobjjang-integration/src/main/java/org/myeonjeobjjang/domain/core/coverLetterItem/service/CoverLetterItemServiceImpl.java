package org.myeonjeobjjang.domain.core.coverLetterItem.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.coverLetter.entity.CoverLetter;
import org.myeonjeobjjang.domain.core.coverLetter.repository.CoverLetterRepository;
import org.myeonjeobjjang.domain.core.coverLetterItem.entity.CoverLetterItem;
import org.myeonjeobjjang.domain.core.coverLetterItem.repository.CoverLetterItemRepository;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.IntegrationCoverLetterItemRequest;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.IntegrationCoverLetterItemResponse;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.JobDescriptionQuestionService;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto.IntegrationJobDescriptionQuestionResponse;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.myeonjeobjjang.domain.core.coverLetter.CoverLetterErrorCode.COVER_LETTER_NOT_FOUND;
import static org.myeonjeobjjang.domain.core.coverLetterItem.CoverLetterItemErrorCode.COVER_LETTER_ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CoverLetterItemServiceImpl implements CoverLetterItemService {
    private final CoverLetterItemRepository coverLetterItemRepository;
    private final CoverLetterRepository coverLetterRepository;

    private final JobDescriptionQuestionService jobDescriptionQuestionService;

    @Override
    public IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse createMoreQuestionByCoverLetterId(Long coverLetterId) {
        CoverLetter coverLetter = coverLetterRepository.findById(coverLetterId)
            .orElseThrow(() -> new BaseException(COVER_LETTER_NOT_FOUND));
        return createMoreQuestion(coverLetter);
    }

    @Override
    public IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse createMoreQuestion(CoverLetter coverLetter) {
        long lastQuestionNumber = coverLetterItemRepository.getLastQuestionNumberByCoverLetter(coverLetter);
        CoverLetterItem newCoverLetterItem = CoverLetterItem.builder()
            .questionNumber(lastQuestionNumber + 1)
            .coverLetter(coverLetter)
            .build();
        CoverLetterItem savedCoverLetterItem = coverLetterItemRepository.save(newCoverLetterItem);
        return new IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse(
            savedCoverLetterItem.getCoverLetterItemId(),
            savedCoverLetterItem.getQuestionNumber(),
            savedCoverLetterItem.getQuestion(),
            savedCoverLetterItem.getAnswer(),
            coverLetter.getCoverLetterId()
        );
    }

    @Override
    public List<IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse> copyQuestionFromJobDescription(IntegrationCoverLetterItemRequest.IntegrationCoverLetterItemCopyFromJobDescriptionRequest request) {
        List<IntegrationJobDescriptionQuestionResponse.IntegrationJobDescriptionQuestionInfoResponse> jobDescriptionQuestionInfoResponses = jobDescriptionQuestionService.findByJobDescription(request.jobDescription());
        List<CoverLetterItem> newCoverLetterItems = jobDescriptionQuestionInfoResponses.stream()
            .map(jdqir -> CoverLetterItem.builder()
                .questionNumber(jdqir.questionNumber())
                .question(jdqir.question())
                .coverLetter(request.coverLetter())
                .build()
            )
            .toList();
        List<CoverLetterItem> savedCoverLetterItems = coverLetterItemRepository.saveAll(newCoverLetterItems);
        return savedCoverLetterItems.stream().map(cli ->
            new IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse(
                cli.getCoverLetterItemId(),
                cli.getQuestionNumber(),
                cli.getQuestion(),
                cli.getAnswer(),
                cli.getCoverLetter().getCoverLetterId()
            )).toList();
    }

    @Override
    public IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse get(Long coverLetterItemId) {
        CoverLetterItem coverLetterItem = coverLetterItemRepository.findById(coverLetterItemId)
            .orElseThrow(() -> new BaseException(COVER_LETTER_ITEM_NOT_FOUND));
        return new IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse(
            coverLetterItem.getCoverLetterItemId(),
            coverLetterItem.getQuestionNumber(),
            coverLetterItem.getQuestion(),
            coverLetterItem.getAnswer(),
            coverLetterItem.getCoverLetter().getCoverLetterId()
        );
    }

    @Override
    @Transactional
    public boolean update(IntegrationCoverLetterItemRequest.IntegrationCoverLetterItemUpdateRequest request) {
        if (request.answer() == null && request.question() == null) return false;
        CoverLetterItem coverLetterItem = coverLetterItemRepository.findById(request.coverLetterItemId())
            .orElseThrow(() -> new BaseException(COVER_LETTER_ITEM_NOT_FOUND));
        coverLetterItem.updateInfo(request.question(), request.answer());
        return true;
    }
}
