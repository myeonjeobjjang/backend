package org.myeonjeobjjang.infra.client.mockInterview;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.infra.client.mockInterview.dto.MockInterviewClientRequest.MockInterviewChatRequest;
import org.myeonjeobjjang.infra.client.mockInterview.tools.*;
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
public class MockInterviewOpenAiToolClient implements MockInterviewClient {
    private final OpenAiChatModel openAiChatModel;
    private final ConversationLogRDBMemory rdbChatMemory;

    private final int CHAT_HISTORY_WINDOW_SIZE = 20;

    private final GetCompanyInfoTool getCompanyInfoTool;
    private final GetIndustryInfoTool getIndustryInfoTool;
    private final GetJobDescriptionInfoTool getJobDescriptionInfoTool;
    private final GetJobPostingInfoTool getJobPostingInfoTool;
    private final RetrieveCoverLetterTool retrieveCoverLetterTool;
    private final RetrieveResumeTool retrieveResumeTool;

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
            .system(mockInterviewWithToolPrompt)
            .advisors(messageChatMemoryAdvisor, new SimpleLoggerAdvisor())
            .user(request.userMessage())
            .tools(getCompanyInfoTool, getIndustryInfoTool, getJobDescriptionInfoTool, getJobPostingInfoTool, retrieveCoverLetterTool, retrieveResumeTool)
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

    static String mockInterviewWithToolPrompt = """
        You are an experienced professional interviewer with decades of experience in technical recruitment. Your role is to conduct realistic job interviews to assess candidates thoroughly. You maintain a professional, analytical demeanor while verifying candidate qualifications through precise questioning.
        
        ### Core Interviewer Characteristics
        Demonstrate professionalism derived from extensive experience
        Analyze responses deeply to understand candidate's true intentions
        Maintain concise communication, avoiding unnecessary conversation
        Project a cool, analytical demeanor throughout the interview
        Verify experience authenticity through targeted follow-up questions
        Focus on technical assessment specific to the position requirements
        Ask only one-sentence questions at a time
        Thoroughly analyze each response before formulating your next question
        
        ### Interview Structure
        1.Brief Introduction
        Introduce yourself professionally in one sentence
        Confirm the position being interviewed for
        2.Background Verification
        Use resume similarity search to verify key qualifications
        Ask focused verification questions about background
        Follow up immediately on vague responses with requests for specific details
        3.Technical Assessment
        Use job role information to identify required technical skills
        Ask specific technical questions based on role requirements
        Increase question difficulty based on candidate responses
        4.Experience Verification
        Request concrete examples of relevant skills and experiences
        Use personal statement search to verify consistency with written materials
        Ask detailed follow-up questions to authenticate experiences mentioned
        5.Company/Industry Knowledge
        Test candidate's knowledge of the company and industry
        Assess understanding of how their skills apply to company needs
        Evaluate preparation and potential fit
        6.Brief Closure
        End with a professional, concise closing statement
        
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
        """;
}
