package org.myeonjeobjjang.domain.core.vectordb;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.resume.entity.Resume;
import org.myeonjeobjjang.domain.core.vectordb.dto.VectorDBRequest.CoverLetterEmbeddingRequest;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Integer coverLetterEmbedding(List<CoverLetterEmbeddingRequest> coverLetterEmbeddingRequests, Long conversationId) {
        Map<String, Object> documentMetadata = new HashMap<>();
        documentMetadata.put("category", "cover_letter");
        documentMetadata.put("conversation_id", conversationId);

        List<Document> documents = coverLetterEmbeddingRequests.stream()
            .map(cler -> {
                documentMetadata.put("question", cler.question());
                return new Document(cler.answer(), documentMetadata);
            }).toList();
        List<Document> splitDocuments = documentSplitter(documents);
        vectorStore.accept(splitDocuments);
        return splitDocuments.size();
    }

    @Override
    public Integer resumeEmbedding(Resume resume, Long conversationId) {
        Map<String, Object> documentMetadata = new HashMap<>();
        documentMetadata.put("category", "resume");
        documentMetadata.put("conversation_id", conversationId);

        Class<Resume> clazz = Resume.class;
        List<Document> documents = new ArrayList<>(8);
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.getName().startsWith("get") || !method.getReturnType().equals(String.class) || method.getParameterCount() > 0)
                continue;
            Object fieldObject = null;
            try {
                fieldObject = method.invoke(resume);
            } catch (IllegalAccessException | InvocationTargetException ignored) {
                continue;
            }
            String value = (String) fieldObject;

            if (!value.isEmpty()) {
                String fieldName = method.getName().substring(3);
                documentMetadata.put("data_of", fieldName);
                documents.add(new Document(value, documentMetadata));
            }
        }

        List<Document> splitDocuments = documentSplitter(documents);
        vectorStore.accept(splitDocuments);
        return splitDocuments.size();
    }

    private List<Document> documentSplitter(List<Document> documents) {
        TextSplitter splitter = new CustomCoverLetterResumeTextSplitter();
        return splitter.apply(documents);
    }

    @Override
    public void deleteEmbedding(String conversationId) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        vectorStore.delete(b.eq("conversation_id", conversationId).build());
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
