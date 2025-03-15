package org.myeonjeobjjang.domain.core.conversationLog.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myeonjeobjjang.domain.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConversationLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conversationLogId;
    @JoinColumn(updatable = false)
    private String conversationId;
    @Column(updatable = false, columnDefinition = "TEXT")
    private String data;
    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private ConversationMessageType messageType;

    @Builder
    private ConversationLog(String conversationId, String data, ConversationMessageType messageType) {
        this.conversationId = conversationId;
        this.data = data;
        this.messageType = messageType;
    }
}

