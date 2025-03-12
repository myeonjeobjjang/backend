package org.myeonjeobjjang.domain.core.conversationLog.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myeonjeobjjang.domain.common.BaseEntity;
import org.myeonjeobjjang.domain.core.conversation.entity.Conversation;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConversationLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conversatoinLogId;
    @JoinColumn(updatable = false)
    @ManyToOne
    private Conversation conversation;
    @Column(updatable = false, columnDefinition = "TEXT")
    private String data;
    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private ConversationMessageType messageType;

    @Builder
    private ConversationLog(Conversation conversation, String data, ConversationMessageType messageType) {
        this.conversation = conversation;
        this.data = data;
        this.messageType = messageType;
    }
}

