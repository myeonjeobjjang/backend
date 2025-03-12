package org.myeonjeobjjang.domain.chat.llmchatlog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LlmChatLogRepository extends JpaRepository<LlmChatLog, Long> {
    @Query("""
            select l
            from LlmChatLog l
            where l.conversationId = :conversationId and l.deletedAt is null
            order by l.createdAt DESC
            limit :lastN
        """)
    List<LlmChatLog> findAllByConversationId(String conversationId, int lastN);

    void deleteAllByConversationId(String conversationId);
}
