package org.myeonjeobjjang.domain.chat.llmchatlog;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LlmChatLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private String conversationId;
    @Column(updatable = false, columnDefinition = "TEXT")
    private String data;
    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private LlmMessageType messageType;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public void delete() {
        deletedAt = LocalDateTime.now();
    }

    @Builder
    LlmChatLog(String conversationId, String data, LlmMessageType messageType) {
        this.conversationId = conversationId;
        this.data = data;
        this.messageType = messageType;
    }
}