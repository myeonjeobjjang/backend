package org.myeonjeobjjang.domain.core.coverLetter.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.coverLetter.entity.CoverLetter;
import org.myeonjeobjjang.domain.core.coverLetter.repository.CoverLetterRepository;
import org.myeonjeobjjang.domain.core.coverLetter.repository.dto.CoverLetterProjection.CoverLetterInfoForConversationProjection;
import org.myeonjeobjjang.domain.core.coverLetter.repository.dto.CoverLetterProjection.CoverLetterInfoProjection;
import org.myeonjeobjjang.domain.core.coverLetter.service.dto.CoverLetterResponse.CoverLetterInfoResponse;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.CoverLetterItemService;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.CoverLetterItemRequest.CoverLetterItemCopyFromJobDescriptionRequest;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.CoverLetterItemResponse.CoverLetterItemInfoResponse;
import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.jobDescription.service.JobDescriptionService;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.domain.core.vectordb.VectorDBService;
import org.myeonjeobjjang.domain.core.vectordb.dto.VectorDBRequest.CoverLetterEmbeddingRequest;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.myeonjeobjjang.domain.core.coverLetter.CoverLetterErrorCode.COVER_LETTER_NOT_FOUND;
import static org.myeonjeobjjang.domain.core.coverLetter.CoverLetterErrorCode.DUPLICATED_COVER_LETTER;

@Service
@RequiredArgsConstructor
public class CoverLetterServiceImpl implements CoverLetterService {
    private final CoverLetterRepository coverLetterRepository;

    private final JobDescriptionService jobDescriptionService;
    private final CoverLetterItemService coverLetterItemService;
    private final VectorDBService vectorDBService;

    @Override
    public CoverLetterInfoResponse createEmpty(Member member) {
        CoverLetter newCoverLetter = CoverLetter.builder()
            .member(member)
            .build();
        CoverLetter savedCoverLetter = coverLetterRepository.save(newCoverLetter);
        CoverLetterItemInfoResponse coverLetterItemInfoResponse = coverLetterItemService
            .createMoreQuestion(savedCoverLetter);
        return CoverLetterInfoResponse.toDto(coverLetterItemInfoResponse);
    }

    @Override
    public CoverLetterInfoResponse createWithJobDescription(Member member, Long jobDescriptionId) {
        JobDescription jobDescription = jobDescriptionService.findById(jobDescriptionId);
        if (coverLetterRepository.findByMemberAndJobDescription(member, jobDescription).isPresent())
            throw new BaseException(DUPLICATED_COVER_LETTER);
        CoverLetter newCoverLetter = CoverLetter.builder()
            .jobDescription(jobDescription)
            .member(member)
            .build();
        CoverLetter savedCoverLetter = coverLetterRepository.save(newCoverLetter);
        List<CoverLetterItemInfoResponse> coverLetterItemInfoResponses = coverLetterItemService
            .copyQuestionFromJobDescription(
                CoverLetterItemCopyFromJobDescriptionRequest.toDto(savedCoverLetter, jobDescription)
            );
        return CoverLetterInfoResponse.toDto(coverLetterItemInfoResponses, jobDescription);
    }

    @Override
    public CoverLetterInfoResponse get(Long coverLetterId) {
        List<CoverLetterInfoProjection> coverLetterInfoProjections = coverLetterRepository.findByCoverLetterId(coverLetterId);
        if (coverLetterInfoProjections.isEmpty())
            throw new BaseException(COVER_LETTER_NOT_FOUND);
        return CoverLetterInfoResponse.toDto(coverLetterInfoProjections);
    }

    @Override
    public int embeddingCoverLetter(Long coverLetterId, Long conversationId) {
        List<CoverLetterInfoProjection> coverLetterInfoProjections = coverLetterRepository.findByCoverLetterId(coverLetterId);
        return vectorDBService.coverLetterEmbedding(
            coverLetterInfoProjections.stream().map(CoverLetterEmbeddingRequest::toDto).toList(),
            conversationId
        );
    }

    @Override
    public CoverLetterInfoForConversationProjection findCoverLetterForConversation(Long coverLetterId) {
        return coverLetterRepository.findCoverLetterForConversation(coverLetterId)
            .orElseThrow(() -> new BaseException(COVER_LETTER_NOT_FOUND));
    }
}
