package org.myeonjeobjjang.domain.core.conversation.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.conversation.entity.Conversation;
import org.myeonjeobjjang.domain.core.conversation.repository.ConversationRepository;
import org.myeonjeobjjang.domain.core.conversation.service.dto.ConversationResponse.ConversationCreateResponse;
import org.myeonjeobjjang.domain.core.coverLetter.repository.dto.CoverLetterProjection.CoverLetterInfoForConversationProjection;
import org.myeonjeobjjang.domain.core.coverLetter.service.CoverLetterService;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.myeonjeobjjang.domain.core.resume.entity.Resume;
import org.myeonjeobjjang.domain.core.resume.service.ResumeService;
import org.myeonjeobjjang.domain.core.vectordb.VectorDBService;
import org.myeonjeobjjang.infra.client.mockInterview.MockInterviewClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {
    private final ConversationRepository conversationRepository;

    private final CoverLetterService coverLetterService;
    private final ResumeService resumeService;
    private final VectorDBService vectorDBService;
    private final MockInterviewClient mockInterviewClient;

    private final String MOCK_INTERVIEW_CONVERSATION_PREFIX = "MOCK_INTERVIEW_";

    @Override
    public ConversationCreateResponse create(Member member, Long coverLetterId, Long resumeId) {
        CoverLetterInfoForConversationProjection projectionResponse = coverLetterService.findCoverLetterForConversation(coverLetterId);
        Resume resume = resumeService.findById(resumeId);
        Conversation newConversation = Conversation.builder()
            .member(member)
            .coverLetter(projectionResponse.getCoverLetter())
            .resume(resume)
            .industryInfo(projectionResponse.getIndustryName() + " : " + projectionResponse.getIndustryInformation())
            .companyInfo(projectionResponse.getCompanyName() + " : " + projectionResponse.getCompanyInformation())
            .jobDescriptionInfo(projectionResponse.getJobName() + " : " + projectionResponse.getDescription())
            .build();
        Conversation savedConversation = conversationRepository.save(newConversation);
        return ConversationCreateResponse.toDto(
            newConversation.getConversationId(),
            coverLetterService.embeddingCoverLetter(coverLetterId, savedConversation.getConversationId()),
            vectorDBService.resumeEmbedding(resume, savedConversation.getConversationId())
        );
    }
}
