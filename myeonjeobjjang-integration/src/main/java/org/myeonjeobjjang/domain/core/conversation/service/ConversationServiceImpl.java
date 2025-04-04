package org.myeonjeobjjang.domain.core.conversation.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.conversation.entity.Conversation;
import org.myeonjeobjjang.domain.core.conversation.repository.ConversationRepository;
import org.myeonjeobjjang.domain.core.conversation.service.dto.ConversationResponse.ConversationCreateResponse;
import org.myeonjeobjjang.domain.core.conversationLog.service.ConversationLogService;
import org.myeonjeobjjang.domain.core.conversationLog.service.dto.ConversationLogResponse.ConversationLogNoOffsetGetResponse;
import org.myeonjeobjjang.domain.core.coverLetter.repository.dto.CoverLetterProjection.CoverLetterInfoForConversationProjection;
import org.myeonjeobjjang.domain.core.coverLetter.service.CoverLetterService;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.domain.core.resume.entity.Resume;
import org.myeonjeobjjang.domain.core.resume.service.ResumeService;
import org.myeonjeobjjang.domain.core.vectordb.VectorDBService;
import org.myeonjeobjjang.exception.BaseException;
import org.myeonjeobjjang.infra.client.mockInterview.MockInterviewOpenAiClient;
import org.myeonjeobjjang.infra.client.mockInterview.MockInterviewOpenAiToolClient;
import org.myeonjeobjjang.infra.client.mockInterview.dto.MockInterviewClientRequest.MockInterviewChatRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.myeonjeobjjang.domain.core.conversation.ConversationErrorCode.CONVERSATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {
    private final ConversationRepository conversationRepository;

    private final CoverLetterService coverLetterService;
    private final ResumeService resumeService;
    private final VectorDBService vectorDBService;
    private final MockInterviewOpenAiClient mockInterviewOpenAiClient;
    private final MockInterviewOpenAiToolClient mockInterviewOpenAiToolClient;
    private final ConversationLogService conversationLogService;

    private final String MOCK_INTERVIEW_CONVERSATION_PREFIX = "MOCK_INTERVIEW_";

    @Override
    public ConversationCreateResponse create(Member member, Long coverLetterId, Long resumeId) {
        CoverLetterInfoForConversationProjection projectionResponse = coverLetterService.findCoverLetterForConversation(coverLetterId);
        Resume resume = resumeService.findById(resumeId);
        Conversation newConversation = Conversation.builder()
            .member(member)
            .coverLetter(projectionResponse.getCoverLetter())
            .resume(resume)
            .industry(projectionResponse.getIndustry())
            .company(projectionResponse.getCompany())
            .jobDescription(projectionResponse.getJobDescription())
            .jobPosting(projectionResponse.getJobPosting())
            .build();
        Conversation savedConversation = conversationRepository.save(newConversation);
        return ConversationCreateResponse.toDto(
            savedConversation.getConversationId(),
            coverLetterService.embeddingCoverLetter(coverLetterId, MOCK_INTERVIEW_CONVERSATION_PREFIX + savedConversation.getConversationId()),
            vectorDBService.resumeEmbedding(resume, MOCK_INTERVIEW_CONVERSATION_PREFIX + savedConversation.getConversationId())
        );
    }

    @Override
    public String mockInterviewChat(Member member, String userMessage, Long conversationId) {
        Conversation conversation = conversationRepository.findByConversationIdAndMember(conversationId, member)
            .orElseThrow(() -> new BaseException(CONVERSATION_NOT_FOUND));

        return mockInterviewOpenAiClient.mockInterviewChat(MockInterviewChatRequest.toDto(
            userMessage,
            conversation,
            MOCK_INTERVIEW_CONVERSATION_PREFIX
        ));
    }

    @Override
    public String mockInterviewToolsChat(Member member, String userMessage, Long conversationId) {
        Conversation conversation = conversationRepository.findByConversationIdAndMember(conversationId, member)
            .orElseThrow(() -> new BaseException(CONVERSATION_NOT_FOUND));
        return mockInterviewOpenAiToolClient.mockInterviewChat(MockInterviewChatRequest.toDto(
            userMessage,
            conversation,
            MOCK_INTERVIEW_CONVERSATION_PREFIX
        ));
    }

    @Override
    public ConversationLogNoOffsetGetResponse noOffsetGetConversationLog(Long conversationId, LocalDateTime lastConversationCreatedAt, Integer amount) {
        return conversationLogService.noOffsetGet(MOCK_INTERVIEW_CONVERSATION_PREFIX + conversationId, lastConversationCreatedAt, amount);
    }
}
