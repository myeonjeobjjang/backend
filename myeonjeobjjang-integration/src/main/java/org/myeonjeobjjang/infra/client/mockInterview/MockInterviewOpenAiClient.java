package org.myeonjeobjjang.infra.client.mockInterview;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.infra.client.mockInterview.dto.MockInterviewClientRequest.MockInterviewChatRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;

import static org.myeonjeobjjang.infra.client.mockInterview.PromptMessage.MOCK_INTERVIEW_SYSTEM_PROMPT;

@Service
@RequiredArgsConstructor
public class MockInterviewOpenAiClient implements MockInterviewClient {
    private static final Logger log = LoggerFactory.getLogger(MockInterviewOpenAiClient.class);
    private final OpenAiChatModel openAiChatModel;
    private final VectorStore vectorStore;
    private final ConversationLogRDBMemory rdbChatMemory;

    private final int CHAT_HISTORY_WINDOW_SIZE = 20;
    private final int MAX_FOLLOW_UP_QUESTION = 2;

    @Override
    public String mockInterviewChat(MockInterviewChatRequest request) {
        String systemMessage = getSystemMessage(request);
        MessageChatMemoryAdvisor messageChatMemoryAdvisor = getMessageChatMemoryAdvisor(request.conversationId());
        RetrievalAugmentationAdvisor ragAdvisor = getRagAdvisor(request.conversationId());
        return ChatClient.builder(openAiChatModel).build()
            .prompt(new Prompt("", OpenAiChatOptions.builder().model(OpenAiApi.ChatModel.GPT_4_O).build()))
            .system(systemMessage)
            .advisors(messageChatMemoryAdvisor, ragAdvisor)
            .user(request.userMessage())
            .call()
            .content();
    }

    private String getSystemMessage(MockInterviewChatRequest request) {
        return String.format(
            MOCK_INTERVIEW_SYSTEM_PROMPT.message,
            request.industryInfo(),
            request.companyInfo(),
            request.jobDescriptionInfo(),
            MAX_FOLLOW_UP_QUESTION
        );
    }

    private MessageChatMemoryAdvisor getMessageChatMemoryAdvisor(String conversationId) {
        return new MessageChatMemoryAdvisor(rdbChatMemory, conversationId, CHAT_HISTORY_WINDOW_SIZE);
    }

    private RetrievalAugmentationAdvisor getRagAdvisor(String conversationId) {
        return RetrievalAugmentationAdvisor.builder()
            .documentRetriever(VectorStoreDocumentRetriever.builder()
                .similarityThreshold(0.8)
                .topK(10)
                .vectorStore(vectorStore)
                .filterExpression(() -> new FilterExpressionBuilder()
                    .or(
                        (new FilterExpressionBuilder()).and(
                            new FilterExpressionBuilder().eq("category", "cover_letter"),
                            new FilterExpressionBuilder().eq("conversation_id", conversationId)
                        ),
                        (new FilterExpressionBuilder()).and(
                            new FilterExpressionBuilder().eq("category", "resume"),
                            new FilterExpressionBuilder().eq("conversation_id", conversationId)
                        )
                    )
                    .build())
                .build())
            .queryAugmenter(ContextualQueryAugmenter.builder()
                .allowEmptyContext(true)
                .build())
            .build();
    }
}
