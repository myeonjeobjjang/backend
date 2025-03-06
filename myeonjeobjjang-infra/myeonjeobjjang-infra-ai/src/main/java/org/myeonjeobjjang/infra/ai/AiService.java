package org.myeonjeobjjang.infra.ai;

import org.myeonjeobjjang.infra.ai.dto.InfraAiRequest;
import org.myeonjeobjjang.infra.ai.dto.InfraAiResponse;

public interface AiService {
    String requestSimpleQuestion(String request);

    Integer embeddingPdf(InfraAiRequest.EmbeddingPdfRequest request);

    InfraAiResponse.TechRecruitmentResponse retrievePdfResult(InfraAiRequest.TechRecruitmentRequest request);
}
