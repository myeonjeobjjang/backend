package org.myeonjeobjjang.domain.core.vectordb;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.resume.entity.Resume;
import org.myeonjeobjjang.domain.core.vectordb.dto.VectorDBRequest.CoverLetterEmbeddingRequest;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
        Map<String, Object> documentMetadata = Map.of("category", "cover_letter", "conversation_id", conversationId);
        StringBuffer sb = new StringBuffer();
        List<Document> documents = coverLetterEmbeddingRequests.stream()
            .map(cler -> {
                sb.delete(0, sb.length());
                sb.append("[Question ").append(cler.questionNumber()).append("] : ").append(cler.question())
                    .append(" [Answer ").append(cler.questionNumber()).append("] : ").append(cler.answer());
                return new Document(sb.toString(), documentMetadata);
            }).toList();
        List<Document> splitDocuments = documentSplitter(documents);
        vectorStore.accept(splitDocuments);
        return documents.size();
    }

    @Override
    public Integer resumeEmbedding(Resume resume, Long conversationId) {
        Map<String, Object> documentMetadata = Map.of("category", "resume", "conversation_id", conversationId);

        Class<Resume> clazz = Resume.class;
        List<Document> documents = new ArrayList<>(8);
        StringBuffer sb = new StringBuffer();
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
                sb.delete(0, sb.length());
                String fieldName = method.getName().substring(3);
                sb.append("[").append(fieldName).append("] : ").append(value);
                documents.add(new Document(sb.toString(), documentMetadata));
            }
        }

        List<Document> splitDocuments = documentSplitter(documents);
        vectorStore.accept(splitDocuments);
        return documents.size();
    }

    private List<Document> documentSplitter(List<Document> documents) {
        TokenTextSplitter splitter = new TokenTextSplitter(800, 350, 5, 10000, true);
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
