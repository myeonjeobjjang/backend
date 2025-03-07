package org.myeonjeobjjang.domain.core.vectordb;

import org.springframework.ai.document.Document;

import java.util.List;

public interface VectorDBService {
    Integer embeddingPdf(List<Document> documents);
    List<Document> retrievedDocs(String query, Integer documentAmount, String fileName, String userName);
}
