package org.myeonjeobjjang.domain.core.coverLetter.service.dto;

import org.myeonjeobjjang.domain.core.coverLetter.repository.dto.CoverLetterProjection;
import org.myeonjeobjjang.domain.core.coverLetterItem.service.dto.CoverLetterItemResponse.CoverLetterItemInfoResponse;
import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;

import java.util.List;

public class CoverLetterResponse {
    public record CoverLetterInfoResponse(
        Long coverLetterId,
        String jobName,
        String description,
        List<CoverLetterItemInfoResponse> coverLetterItemInfos
    ) {
        public static CoverLetterInfoResponse toDto(CoverLetterItemInfoResponse coverLetterItemInfoResponse) {
            return new CoverLetterInfoResponse(
                coverLetterItemInfoResponse.coverLetterId(),
                null, null,
                List.of(coverLetterItemInfoResponse)
            );
        }

        public static CoverLetterInfoResponse toDto(List<CoverLetterItemInfoResponse> coverLetterItemInfoResponses, JobDescription jobDescription) {
            return new CoverLetterInfoResponse(
                coverLetterItemInfoResponses.get(0).coverLetterId(),
                jobDescription.getJobName(),
                jobDescription.getDescription(),
                coverLetterItemInfoResponses
            );
        }

        public static CoverLetterInfoResponse toDto(List<CoverLetterProjection.CoverLetterInfoProjection> coverLetterInfoProjections) {
            return new CoverLetterInfoResponse(
                coverLetterInfoProjections.get(0).getCoverLetterId(),
                coverLetterInfoProjections.get(0).getJobName(),
                coverLetterInfoProjections.get(0).getDescription(),
                coverLetterInfoProjections.stream().map(CoverLetterItemInfoResponse::toDto).toList()
            );
        }
    }
}
