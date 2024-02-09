package fr.dtek.lab.jpa;

import fr.dtek.lab.jpa.enums.MessageByEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
public class MessageEntity {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @Column(name = "id", columnDefinition = "VARCHAR(255)", insertable = false, updatable = false, nullable = false)
    private String id;

    private String userId;

    @Enumerated(EnumType.STRING)
    private MessageByEnum messageBy;

    private LocalDateTime createdAt;

    @Lob
    private String messageText;
}