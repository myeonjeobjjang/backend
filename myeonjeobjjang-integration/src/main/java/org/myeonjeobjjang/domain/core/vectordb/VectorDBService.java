package org.myeonjeobjjang.domain.core.vectordb;

import org.myeonjeobjjang.domain.core.resume.entity.Resume;
import org.myeonjeobjjang.domain.core.vectordb.dto.VectorDBRequest.CoverLetterEmbeddingRequest;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.Map;

public interface VectorDBService {
    Integer embeddingPdf(List<Document> documents);

    Integer coverLetterEmbedding(List<CoverLetterEmbeddingRequest> coverLetterEmbeddingRequests, String conversationId);

    Integer resumeEmbedding(Resume resume, String conversationId);

    void deleteEmbedding(String conversationId);

    List<Document> retrievedDocs(String query, Integer documentAmount, String fileName, String userName);

    List<Document> retrievedDocs(String query, Integer topK, Map<String, Object> documentMetadata);
}
