package org.myeonjeobjjang.domain.core.conversationLog.repository;

import org.myeonjeobjjang.domain.core.conversationLog.entity.ConversationLog;
import org.myeonjeobjjang.domain.core.conversationLog.repository.dto.ConversationLogProjection.PassedConversationLogProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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

    @Query(value = """
        select
        tmp.conversation_log_id conversationLogId,
        tmp.data data,
        tmp.message_type messageType,
        min(tmp.created_at) over() as lastConversationCreatedAt
        from (
            select
                cl.conversation_log_id conversation_log_id,
                cl.data data,
                cl.message_type message_type,
                cl.created_at created_at
            from conversation_log cl
            where cl.conversation_id = (:conversationId)::text
            and (cl.created_at < (:lastConversationCreatedAt)::timestamp or (:lastConversationCreatedAt)::timestamp is null)
            order by cl.created_at desc
            limit (:limit)::bigint
        ) tmp
        """, nativeQuery = true)
    List<PassedConversationLogProjection> getPassedConversationLog(
        @Param("conversationId") String conversationId,
        @Param("lastConversationCreatedAt") LocalDateTime lastConversationCreatedAt,
        @Param("limit") Integer limit
    );
}
