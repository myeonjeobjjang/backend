package org.myeonjeobjjang.techRecruit;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.infra.ai.AiService;
import org.myeonjeobjjang.infra.ai.dto.InfraAiRequest;
import org.myeonjeobjjang.techRecruit.dto.AiRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AiController {
    private final AiService aiService;

    @GetMapping
    public String simpleQuestion(@RequestParam String question) {
        return aiService.requestSimpleQuestion(question);
    }

    @PostMapping("/pdf/embedding")
    public Integer embeddingPdf(@RequestBody @Valid AiRequest.EmbeddingRequest request) {
        return aiService.embeddingPdf(InfraAiRequest.EmbeddingPdfRequest.builder()
            .fileName(request.fileName())
            .userName(request.userName())
            .build());
    }

    @PostMapping("/pdf/retrieve")
    public Object retrievePdfResult(@RequestBody @Valid AiRequest.RetreieveRequest request) {
        return aiService.retrievePdfResult(InfraAiRequest.TechRecruitmentRequest.builder()
            .companyName(request.companyName())
            .fileName(request.fileName())
            .userName(request.userName())
            .build());
    }

    @PostMapping(value = "/memory", produces = "text/event-stream")
    public Flux<String> simpleMemoryChat(@RequestBody AiRequest.SimpleMemoryChatRequest request) {
        return aiService.memoryStreamChat(request.content(), request.userId());
    }
}
