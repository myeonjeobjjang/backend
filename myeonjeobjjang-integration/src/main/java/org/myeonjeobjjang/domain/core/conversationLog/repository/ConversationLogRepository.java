package org.myeonjeobjjang.domain.core.conversationLog.repository;

import org.myeonjeobjjang.domain.core.conversationLog.entity.ConversationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConversationLogRepository extends JpaRepository<ConversationLog, Long> {
    @Query("""
        select cl
        from ConversationLog cl
        where cl.conversationId = :conversationId and cl.deletedAt is null
        order by cl.createdAt DESC
        limit :lastN
        """)
    List<ConversationLog> findAllByConversationId(String conversationId, int lastN);

    void deleteAllByConversationId(String conversationId);
}
