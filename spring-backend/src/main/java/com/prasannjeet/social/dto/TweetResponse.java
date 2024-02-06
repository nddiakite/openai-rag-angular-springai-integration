package com.prasannjeet.social.dto;

import java.util.Objects;

public class TweetResponse {
    private final String tweetId;
    private final String tweetLink;

    public TweetResponse(String tweetId, String tweetLink) {
        this.tweetId = tweetId;
        this.tweetLink = tweetLink;
    }

    public String getTweetId() {
        return tweetId;
    }

    public String getTweetLink() {
        return tweetLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TweetResponse that = (TweetResponse) o;
        return Objects.equals(tweetId, that.tweetId) &&
                Objects.equals(tweetLink, that.tweetLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tweetId, tweetLink);
    }

    @Override
    public String toString() {
        return "TweetResponse{" +
                "tweetId='" + tweetId + '\'' +
                ", tweetLink='" + tweetLink + '\'' +
                '}';
    }
}
