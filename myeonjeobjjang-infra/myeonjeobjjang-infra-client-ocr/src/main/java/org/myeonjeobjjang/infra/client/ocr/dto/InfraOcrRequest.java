package org.myeonjeobjjang.infra.client.ocr.dto;

import lombok.Builder;

public class InfraOcrRequest {
    @Builder
    public record InfraOcrDocumentNameRequest(
        String fileName,
        String userName
    ) {
    }
}
