package com.prasannjeet.social.jpa;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import com.prasannjeet.social.jpa.enums.MessageByEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
public class MessageEntity {
    @Id
    private String messageId;
    private String userId;
    @Enumerated(EnumType.STRING)
    private MessageByEnum messageBy;
    private LocalDateTime createdAt;
    @Lob
    private String messageText;

}