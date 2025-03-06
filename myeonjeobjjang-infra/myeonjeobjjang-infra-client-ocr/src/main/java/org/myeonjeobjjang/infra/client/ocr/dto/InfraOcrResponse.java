package org.myeonjeobjjang.infra.client.ocr.dto;

import lombok.Builder;
import org.springframework.ai.document.Document;

import java.util.List;

public class InfraOcrResponse {
    @Builder
    public record InfraOcrDocumentNameResponse(
        List<Document> documents
    ) {
    }
}
