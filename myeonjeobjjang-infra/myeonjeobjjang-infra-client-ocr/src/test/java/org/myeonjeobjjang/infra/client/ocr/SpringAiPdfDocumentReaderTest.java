package org.myeonjeobjjang.infra.client.ocr;

import org.junit.jupiter.api.Test;
import org.myeonjeobjjang.infra.client.ocr.dto.InfraOcrRequest;
import org.myeonjeobjjang.infra.client.ocr.dto.InfraOcrResponse;

import static org.junit.jupiter.api.Assertions.*;

class SpringAiPdfDocumentReaderTest {
    SpringAiPdfDocumentReader springAiPdfDocumentReader = new SpringAiPdfDocumentReader();
    @Test
    void getDocument() {
        // given
        String fileName = "dummy.pdf";
        String userName = "dummy";
        InfraOcrRequest.InfraOcrDocumentNameRequest request = InfraOcrRequest.InfraOcrDocumentNameRequest.builder()
            .fileName(fileName)
            .userName(userName)
            .build();

        //when
        InfraOcrResponse.InfraOcrDocumentNameResponse document = springAiPdfDocumentReader.getDocument(request);

        // then
        assertEquals(1, document.documents().size());
        assertEquals("Dummy PDF file", document.documents().get(0).getText().replaceAll("\\s+", " "));
        assertEquals(userName, document.documents().get(0).getMetadata().get("user_name"));
    }
}