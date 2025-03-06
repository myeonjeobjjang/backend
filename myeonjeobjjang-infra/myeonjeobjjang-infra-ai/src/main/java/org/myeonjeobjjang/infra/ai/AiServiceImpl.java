package org.myeonjeobjjang.infra.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.vectordb.VectorDBService;
import org.myeonjeobjjang.infra.ai.dto.InfraAiRequest;
import org.myeonjeobjjang.infra.ai.dto.InfraAiResponse;
import org.myeonjeobjjang.infra.client.llm.LlmClientService;
import org.myeonjeobjjang.infra.client.llm.dto.InfraClientLlmRequest;
import org.myeonjeobjjang.infra.client.ocr.OcrService;
import org.myeonjeobjjang.infra.client.ocr.dto.InfraOcrResponse;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.myeonjeobjjang.infra.client.llm.PromptMessage.*;
import static org.myeonjeobjjang.infra.client.ocr.dto.InfraOcrRequest.InfraOcrDocumentNameRequest;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {
    private final LlmClientService llmClientService;
    private final OcrService ocrService;
    private final VectorDBService vectorDBService;
    private final int RAG_DOCUMENT_AMOUNT = 8;

    @Override
    public String requestSimpleQuestion(String request) {
        return llmClientService.simpleQuestion(request);
    }

    @Override
    public Integer embeddingPdf(InfraAiRequest.EmbeddingPdfRequest request) {
        InfraOcrResponse.InfraOcrDocumentNameResponse document = ocrService.getDocument(InfraOcrDocumentNameRequest.builder()
            .fileName(request.fileName())
            .userName(request.userName())
            .build());
        // domain-vectordb
        return vectorDBService.embeddingPdf(document.documents());
    }

    @Override
    public InfraAiResponse.TechRecruitmentResponse retrievePdfResult(InfraAiRequest.TechRecruitmentRequest request) {
        // prompt
        String userQuestion = String.format(TechRecruitment_UserQuestion.message, request.companyName());

        // retrieve
        List<String> retrievedStrings = vectorDBService.retrievedDocs(
            userQuestion,
            RAG_DOCUMENT_AMOUNT,
            request.fileName(),
            request.userName()
        ).stream().map(Document::getText).toList();

        // load
        try {
            return llmClientService.getPromptedLlmResponse(
                InfraClientLlmRequest.PromptedLlmRequest.<InfraAiResponse.TechRecruitmentResponse>builder()
                    .retrievedStrings(retrievedStrings)
                    .userQuestion(userQuestion)
                    .systemPromptMessage(TechRecruitment_System)
                    .userPromptMessage(TechRecruitment_UserMessagePrompt)
                    .responseType(InfraAiResponse.TechRecruitmentResponse.class)
                    .build()
            );
        } catch (JsonProcessingException e) {
            return InfraAiResponse.TechRecruitmentResponse.builder().build();
        }
    }
}
