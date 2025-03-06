package org.myeonjeobjjang.domain.core.vectordb;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VectorDBServiceImpl implements VectorDBService {
    private final VectorStore vectorStore;

    @Override
    public Integer embeddingPdf(List<Document> documents) {
        vectorStore.accept(documents);
        return documents.size();
    }

    @Override
    public List<Document> retrievedDocs(String query, Integer documentAmount, String fileName, String userName) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        return vectorStore.similaritySearch(SearchRequest.builder()
                .query(query)
                .topK(documentAmount)
                .filterExpression(b.and(b.eq("file_name", fileName), b.eq("user_name", userName)).build())
                .build()
        );
    }
}
