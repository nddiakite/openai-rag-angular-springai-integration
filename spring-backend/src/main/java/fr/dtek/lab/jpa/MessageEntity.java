package fr.dtek.lab.jpa;

import fr.dtek.lab.jpa.enums.MessageByEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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