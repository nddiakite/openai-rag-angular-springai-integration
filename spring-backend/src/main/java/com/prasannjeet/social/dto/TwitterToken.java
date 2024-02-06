package com.prasannjeet.social.dto;

import java.util.Objects;

public class TwitterToken {
    private final String oauthToken;
    private final String oauthTokenSecret;
    private final String name;
    private final String twitterUserId;
    private final String accountLinkUrl;

    public TwitterToken(String oauthToken, String oauthTokenSecret, String name, String twitterUserId, String accountLinkUrl) {
        this.oauthToken = oauthToken;
        this.oauthTokenSecret = oauthTokenSecret;
        this.name = name;
        this.twitterUserId = twitterUserId;
        this.accountLinkUrl = accountLinkUrl;
    }

    public String oauthToken() {
        return oauthToken;
    }

    public String oauthTokenSecret() {
        return oauthTokenSecret;
    }

    public String getName() {
        return name;
    }

    public String twitterUserId() {
        return twitterUserId;
    }

    public String getAccountLinkUrl() {
        return accountLinkUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TwitterToken that = (TwitterToken) o;
        return Objects.equals(oauthToken, that.oauthToken) &&
                Objects.equals(oauthTokenSecret, that.oauthTokenSecret) &&
                Objects.equals(name, that.name) &&
                Objects.equals(twitterUserId, that.twitterUserId) &&
                Objects.equals(accountLinkUrl, that.accountLinkUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oauthToken, oauthTokenSecret, name, twitterUserId, accountLinkUrl);
    }

    @Override
    public String toString() {
        return "TwitterToken{" +
                "oauthToken='" + oauthToken + '\'' +
                ", oauthTokenSecret='" + oauthTokenSecret + '\'' +
                ", name='" + name + '\'' +
                ", twitterUserId='" + twitterUserId + '\'' +
                ", accountLinkUrl='" + accountLinkUrl + '\'' +
                '}';
    }

    public String name() {
        return this.name;
    }
}
