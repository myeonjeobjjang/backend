package org.myeonjeobjjang.infra.client.mockInterview.supervisorPattern;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.infra.client.mockInterview.memory.ConversationLogRDBMemory;
import org.myeonjeobjjang.infra.client.mockInterview.MockInterviewClient;
import org.myeonjeobjjang.infra.client.mockInterview.dto.MockInterviewClientRequest.MockInterviewChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MockInterviewSupervisorAgentClient implements MockInterviewClient {
    private final OpenAiChatModel openAiChatModel;
    private final ConversationLogRDBMemory rdbChatMemory;

    private final int CHAT_HISTORY_WINDOW_SIZE = 20;

    private final MockInterviewSubordinateAgent mockInterviewSubordinateAgent;

    /**
    채용 관리자(Hiring Supervisor/Hiring Manager/Recruitment Manager)<BR>
    - 역할: 면접 프로세스 총괄 및 최종 결정<BR>
    - 태도: 면접 전반을 조율하며 공정성을 유지하고, 지원자의 잠재력을 탐구<BR>
    - 기대: 회사의 목표와 비전에 부합하는 인재를 찾고, 지원자가 직무를 통해 기여할 수 있는지를 평가<BR>
    - 주요 역할: 면접 과정의 총괄 책임자로, 면접 패널을 구성하고 면접 질문을 조율하며 최종 결정을 내립니다.<BR>
    - 채용 관리자는 직무 설명서 작성, 평가 기준 설정, 그리고 면접 프로세스의 일관성을 유지하는 데 중요한 역할을 합니다.
    */
    @Override
    public String mockInterviewChat(MockInterviewChatRequest request) {
        MessageChatMemoryAdvisor messageChatMemoryAdvisor = getMessageChatMemoryAdvisor(request.conversationId());
        return ChatClient.builder(openAiChatModel).build()
            .prompt()
            .options(OpenAiChatOptions.builder()
                .temperature(1.0)
                .model(OpenAiApi.ChatModel.GPT_4_O)
                .metadata(Map.of("conversationId", request.conversationId()))
                .build()
            )
            .system("""
                You are a Hiring Supervisor managing a mock interview process.s
                Your role is to **delegate all questions** to domain-specific agents (Subordinates) and synthesize their feedback.s
                Never answer directly — always consult experts.
                
                ### Rules:
                1. For each candidate response:
                   - Identify which agent (HR Specialist, Technical Expert, Subject Matter Expert, External Panel Member, Team Member, Executive) should evaluate it.
                   - Forward the query to that agent using the appropriate tool.
                2. Wait for the agent's response before proceeding.
                3. Synthesize the agent's feedback into a follow-up question for the candidate.
                
                ### Question Guidelines
                Limit each question to exactly one sentence
                After each response, thoroughly analyze before proceeding to next question
                For vague answers, immediately request specific examples
                Verify technical skills through targeted, job-relevant questions
                Check consistency between verbal claims and written materials
                For each significant experience claimed, ask at least one verification follow-up question
                Look for specific details that only someone with genuine experience would know
                Your primary objective is to conduct a challenging, thorough interview that effectively assesses the candidate's technical qualifications and experience authenticity without unnecessary conversation.
                
                You have access to tools to retrieve specific information
                
                Answer in Korean.
                """)
            .advisors(messageChatMemoryAdvisor, new SimpleLoggerAdvisor())
            .user(request.userMessage())
            .tools(mockInterviewSubordinateAgent)
            .toolContext(Map.of(
                "industryId", request.industryId(),
                "companyId", request.companyId(),
                "jobDescriptionId", request.jobDescriptionId(),
                "jobPostingId", request.jobPostingId(),
                "conversationId", request.conversationId()
            ))
            .call()
            .content();
    }

    private MessageChatMemoryAdvisor getMessageChatMemoryAdvisor(String conversationId) {
        return new MessageChatMemoryAdvisor(rdbChatMemory, conversationId, CHAT_HISTORY_WINDOW_SIZE);
    }
}
