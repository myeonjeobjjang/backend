package org.myeonjeobjjang.infra.client.ocr;

import org.myeonjeobjjang.infra.client.ocr.dto.InfraOcrRequest;
import org.myeonjeobjjang.infra.client.ocr.dto.InfraOcrResponse;

public interface OcrService {
    InfraOcrResponse.InfraOcrDocumentNameResponse getDocument(InfraOcrRequest.InfraOcrDocumentNameRequest request);
}
