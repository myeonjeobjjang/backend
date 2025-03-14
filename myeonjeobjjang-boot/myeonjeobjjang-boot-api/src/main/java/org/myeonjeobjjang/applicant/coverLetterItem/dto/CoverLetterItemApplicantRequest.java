package org.myeonjeobjjang.applicant.coverLetterItem.dto;

public class CoverLetterItemApplicantRequest {
    public record CoverLetterItemUpdateApplicantRequest(
        String question,
        String answer
    ) {}
}
