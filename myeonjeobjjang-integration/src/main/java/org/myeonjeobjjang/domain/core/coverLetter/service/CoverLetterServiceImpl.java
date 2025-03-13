package org.myeonjeobjjang.domain.core.coverLetter.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.coverLetter.entity.CoverLetter;
import org.myeonjeobjjang.domain.core.coverLetter.repository.CoverLetterRepository;
import org.myeonjeobjjang.domain.core.coverLetter.repository.dto.CoverLetterProjection;
import org.myeonjeobjjang.domain.core.coverLetter.service.dto.IntegrationCoverLetterResponse;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.CoverLetterItemService;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.IntegrationCoverLetterItemRequest;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.IntegrationCoverLetterItemResponse;
import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.jobDescription.service.JobDescriptionService;
import org.myeonjeobjjang.domain.core.member.entity.Member;
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

    @Override
    public IntegrationCoverLetterResponse.IntegrationCoverLetterInfoResponse createEmpty(Member member) {
        CoverLetter newCoverLetter = CoverLetter.builder()
            .member(member)
            .build();
        CoverLetter savedCoverLetter = coverLetterRepository.save(newCoverLetter);
        IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse integrationCoverLetterItemInfoResponses = coverLetterItemService.createMoreQuestion(
            savedCoverLetter
        );
        return new IntegrationCoverLetterResponse.IntegrationCoverLetterInfoResponse(
            savedCoverLetter.getCoverLetterId(),
            null, null,
            List.of(integrationCoverLetterItemInfoResponses)
        );
    }

    @Override
    public IntegrationCoverLetterResponse.IntegrationCoverLetterInfoResponse createWithJobDescription(Member member, Long jobDescriptionId) {
        JobDescription jobDescription = jobDescriptionService.findById(jobDescriptionId);
        if (coverLetterRepository.findByMemberAndJobDescription(member, jobDescription).isPresent())
            throw new BaseException(DUPLICATED_COVER_LETTER);
        CoverLetter newCoverLetter = CoverLetter.builder()
            .jobDescription(jobDescription)
            .member(member)
            .build();
        CoverLetter savedCoverLetter = coverLetterRepository.save(newCoverLetter);
        List<IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse> integrationCoverLetterItemInfoResponses = coverLetterItemService.copyQuestionFromJobDescription(
            new IntegrationCoverLetterItemRequest.IntegrationCoverLetterItemCopyFromJobDescriptionRequest(
                savedCoverLetter,
                jobDescription
            ));
        return new IntegrationCoverLetterResponse.IntegrationCoverLetterInfoResponse(
            savedCoverLetter.getCoverLetterId(),
            jobDescription.getJobName(),
            jobDescription.getDescription(),
            integrationCoverLetterItemInfoResponses
        );
    }

    @Override
    public IntegrationCoverLetterResponse.IntegrationCoverLetterInfoResponse get(Long coverLetterId) {
        List<CoverLetterProjection.CoverLetterInfoProjection> coverLetterInfoProjections = coverLetterRepository.findByCoverLetterId(coverLetterId);
        if (coverLetterInfoProjections.isEmpty())
            throw new BaseException(COVER_LETTER_NOT_FOUND);
        return new IntegrationCoverLetterResponse.IntegrationCoverLetterInfoResponse(
            coverLetterId, coverLetterInfoProjections.get(0).getJobName(), coverLetterInfoProjections.get(0).getDescription(),
            coverLetterInfoProjections.stream().map(clip ->
                new IntegrationCoverLetterItemResponse.IntegrationCoverLetterItemInfoResponse(
                    clip.getCoverLetterItemId(),
                    clip.getQuestionNumber(),
                    clip.getQuestion(),
                    clip.getAnswer(),
                    clip.getCoverLetterId()
                )).toList()
        );
    }
}
