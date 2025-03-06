package org.myeonjeobjjang.infra.client.ocr;

import org.myeonjeobjjang.infra.client.ocr.dto.InfraOcrRequest;
import org.myeonjeobjjang.infra.client.ocr.dto.InfraOcrResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringAiPdfDocumentReader implements OcrService {
    @Override
    public InfraOcrResponse.InfraOcrDocumentNameResponse getDocument(InfraOcrRequest.InfraOcrDocumentNameRequest request) {
        Resource pdfResource = getFileWithFileName(request.fileName());
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfResource, getDefaultPdfDocReaderConfig());
        List<Document> documents = pdfReader.get();

        TokenTextSplitter splitter = new TokenTextSplitter(1000, 400, 10, 5000, true);
        List<Document> splitDocuments = splitter.apply(documents);
        putUserName2Docs(splitDocuments, request.userName());
        return InfraOcrResponse.InfraOcrDocumentNameResponse.builder()
            .documents(splitDocuments)
            .build();
    }

    private Resource getFileWithFileName(String fileName) {
        return new ClassPathResource(String.format("pdf/%s", fileName));
    }

    private PdfDocumentReaderConfig getDefaultPdfDocReaderConfig() {
        return PdfDocumentReaderConfig.builder()
            .withPageTopMargin(0)
            .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                .withNumberOfTopTextLinesToDelete(0)
                .build())
            .withPagesPerDocument(1)
            .build();
    }

    private void putUserName2Docs(List<Document> documents, String userName) {
        for (Document document : documents) {
            document.getMetadata().put("user_name", userName);
        }
    }
}
