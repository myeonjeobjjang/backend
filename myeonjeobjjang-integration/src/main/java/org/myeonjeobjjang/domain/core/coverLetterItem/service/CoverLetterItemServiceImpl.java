package org.myeonjeobjjang.domain.core.coverLetterItem.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.coverLetter.entity.CoverLetter;
import org.myeonjeobjjang.domain.core.coverLetter.repository.CoverLetterRepository;
import org.myeonjeobjjang.domain.core.coverLetterItem.entity.CoverLetterItem;
import org.myeonjeobjjang.domain.core.coverLetterItem.repository.CoverLetterItemRepository;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.CoverLetterItemRequest.CoverLetterItemCopyFromJobDescriptionRequest;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.CoverLetterItemRequest.CoverLetterItemUpdateRequest;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.CoverLetterItemResponse.CoverLetterItemInfoResponse;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.JobDescriptionQuestionService;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto.JobDescriptionQuestionResponse.JobDescriptionQuestionInfoResponse;
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
    public CoverLetterItemInfoResponse createMoreQuestionByCoverLetterId(Long coverLetterId) {
        CoverLetter coverLetter = coverLetterRepository.findById(coverLetterId)
            .orElseThrow(() -> new BaseException(COVER_LETTER_NOT_FOUND));
        return createMoreQuestion(coverLetter);
    }

    @Override
    public CoverLetterItemInfoResponse createMoreQuestion(CoverLetter coverLetter) {
        long lastQuestionNumber = coverLetterItemRepository.getLastQuestionNumberByCoverLetter(coverLetter);
        CoverLetterItem newCoverLetterItem = CoverLetterItem.builder()
            .questionNumber(lastQuestionNumber + 1)
            .coverLetter(coverLetter)
            .build();
        CoverLetterItem savedCoverLetterItem = coverLetterItemRepository.save(newCoverLetterItem);
        return CoverLetterItemInfoResponse.toDto(savedCoverLetterItem);
    }

    @Override
    public List<CoverLetterItemInfoResponse> copyQuestionFromJobDescription(CoverLetterItemCopyFromJobDescriptionRequest request) {
        List<JobDescriptionQuestionInfoResponse> jobDescriptionQuestionInfoResponses = jobDescriptionQuestionService
            .findByJobDescription(request.jobDescription());
        List<CoverLetterItem> newCoverLetterItems = jobDescriptionQuestionInfoResponses.stream()
            .map(jdqir -> CoverLetterItem.builder()
                .questionNumber(jdqir.questionNumber())
                .question(jdqir.question())
                .coverLetter(request.coverLetter())
                .build()
            )
            .toList();
        List<CoverLetterItem> savedCoverLetterItems = coverLetterItemRepository.saveAll(newCoverLetterItems);
        return savedCoverLetterItems.stream()
            .map(CoverLetterItemInfoResponse::toDto).toList();
    }

    @Override
    public CoverLetterItemInfoResponse get(Long coverLetterItemId) {
        CoverLetterItem coverLetterItem = coverLetterItemRepository.findById(coverLetterItemId)
            .orElseThrow(() -> new BaseException(COVER_LETTER_ITEM_NOT_FOUND));
        return CoverLetterItemInfoResponse.toDto(coverLetterItem);
    }

    @Override
    @Transactional
    public boolean update(CoverLetterItemUpdateRequest request) {
        if (request.answer() == null && request.question() == null) return false;
        CoverLetterItem coverLetterItem = coverLetterItemRepository.findById(request.coverLetterItemId())
            .orElseThrow(() -> new BaseException(COVER_LETTER_ITEM_NOT_FOUND));
        coverLetterItem.updateInfo(request.question(), request.answer());
        return true;
    }
}
