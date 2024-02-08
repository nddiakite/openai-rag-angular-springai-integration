package com.prasannjeet.social.jpa;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tweets")
@Data
@NoArgsConstructor
public class TweetEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", columnDefinition = "VARCHAR(255)", insertable = false, updatable = false, nullable = false)
    private String id;

    private String keycloakId;
    private String twitterId;
    private String tweetId;
    private String screenName;
    private String tweetText;
    private String media;
    private LocalDateTime tweetCreatedAt;

    public TweetEntity(String keycloakId, String twitterId, String tweetId, String screenName, String tweetText, String media, LocalDateTime tweetCreatedAt) {
        this.keycloakId = keycloakId;
        this.twitterId = twitterId;
        this.tweetId = tweetId;
        this.screenName = screenName;
        this.tweetText = tweetText;
        this.media = media;
        this.tweetCreatedAt = tweetCreatedAt;
    }
}
