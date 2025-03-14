package org.myeonjeobjjang.applicant.coverLetterItem.dto;

public class CoverLetterItemApplicantResponse {
    public record CoverLetterItemUpdateApplicantResponse(
        boolean isUpdated
    ) {
        public static CoverLetterItemUpdateApplicantResponse toDto(boolean isUpdated) {
            return new CoverLetterItemUpdateApplicantResponse(isUpdated);
        }
    }
}
