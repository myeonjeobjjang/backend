package org.myeonjeobjjang.techRecruit;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.infra.ai.AiService;
import org.myeonjeobjjang.infra.ai.dto.InfraAiRequest;
import org.myeonjeobjjang.techRecruit.dto.TechRecruitRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TechRecruitController {
    private final AiService aiService;

    @GetMapping
    public String simpleQuestion(@RequestParam String question) {
        return aiService.requestSimpleQuestion(question);
    }

    @PostMapping("/pdf/embedding")
    public Integer embeddingPdf(@RequestBody @Valid TechRecruitRequest.EmbeddingRequest request) {
        return aiService.embeddingPdf(InfraAiRequest.EmbeddingPdfRequest.builder()
            .fileName(request.fileName())
            .userName(request.userName())
            .build());
    }

    @PostMapping("/pdf/retrieve")
    public Object retrievePdfResult(@RequestBody @Valid TechRecruitRequest.RetreieveRequest request) {
        return aiService.retrievePdfResult(InfraAiRequest.TechRecruitmentRequest.builder()
            .companyName(request.companyName())
            .fileName(request.fileName())
            .userName(request.userName())
            .build());
    }
}
