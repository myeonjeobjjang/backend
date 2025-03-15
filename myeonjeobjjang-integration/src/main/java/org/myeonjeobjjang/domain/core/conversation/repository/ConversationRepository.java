package org.myeonjeobjjang.domain.core.conversation.repository;

import org.myeonjeobjjang.domain.core.conversation.entity.Conversation;
import org.myeonjeobjjang.domain.core.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByConversationIdAndMember(Long conversationId, Member member);
}
