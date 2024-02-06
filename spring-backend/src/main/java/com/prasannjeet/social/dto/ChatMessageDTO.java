package com.prasannjeet.social.dto;

import java.util.Objects;

public class ChatMessageDTO {
    private final String author;
    private final String createdAt;
    private final String id;
    private final String text;
    private final String type;

    public ChatMessageDTO(String author, String createdAt, String id, String text, String type) {
        this.author = author;
        this.createdAt = createdAt;
        this.id = id;
        this.text = text;
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessageDTO that = (ChatMessageDTO) o;
        return Objects.equals(author, that.author) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(id, that.id) &&
                Objects.equals(text, that.text) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, createdAt, id, text, type);
    }

    @Override
    public String toString() {
        return "ChatMessageDTO{" +
                "author='" + author + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

