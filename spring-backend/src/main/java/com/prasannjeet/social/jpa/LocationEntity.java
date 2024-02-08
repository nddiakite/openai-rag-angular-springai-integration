package com.prasannjeet.social.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tweetlocations")
@Data
@NoArgsConstructor
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String tweetId;
    private String latitude;
    private String longitude;

    public LocationEntity(String tweetId, String latitude, String longitude) {
        this.tweetId = tweetId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
