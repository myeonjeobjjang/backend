package org.myeonjeobjjang.domain.core.conversation.repository;

import org.myeonjeobjjang.domain.core.conversation.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation,Long> {
}
