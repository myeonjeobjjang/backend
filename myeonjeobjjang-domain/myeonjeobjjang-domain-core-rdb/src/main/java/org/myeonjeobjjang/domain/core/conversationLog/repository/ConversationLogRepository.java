package org.myeonjeobjjang.domain.core.conversationLog.repository;

import org.myeonjeobjjang.domain.core.conversationLog.entity.ConversationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationLogRepository extends JpaRepository<ConversationLog,Long> {
}
