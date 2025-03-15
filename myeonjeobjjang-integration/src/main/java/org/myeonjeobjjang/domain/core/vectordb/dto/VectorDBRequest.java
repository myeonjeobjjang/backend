package org.myeonjeobjjang.domain.core.vectordb.dto;

import org.myeonjeobjjang.domain.core.coverLetter.repository.dto.CoverLetterProjection;

public class VectorDBRequest {
    public record CoverLetterEmbeddingRequest(
        Long questionNumber,
        String question,
        String answer
    ) {
        public static CoverLetterEmbeddingRequest toDto(CoverLetterProjection.CoverLetterInfoProjection projection) {
            return new CoverLetterEmbeddingRequest(projection.getQuestionNumber(), projection.getQuestion(), projection.getAnswer());
        }
    }
}
