package com.weareholidays.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TripSettings.
 */
@Entity
@Table(name = "trip_settings")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TripSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "facebook")
    private Boolean facebook;

    @Column(name = "twitter")
    private Boolean twitter;

    @Column(name = "instagram")
    private Boolean instagram;

    @Column(name = "location")
    private Boolean location;

    @Column(name = "sync")
    private Boolean sync;

    @Column(name = "check_in")
    private Boolean checkIn;

    @Column(name = "camer_roll")
    private Boolean camerRoll;

    @Column(name = "camera_roll_sync_time")
    private Long cameraRollSyncTime;

    @Column(name = "facebook_sync_time")
    private Long facebookSyncTime;

    @Column(name = "twitter_since_id")
    private Long twitterSinceId;

    @Column(name = "instagram_sync_time")
    private Long instagramSyncTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isFacebook() {
        return facebook;
    }

    public TripSettings facebook(Boolean facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(Boolean facebook) {
        this.facebook = facebook;
    }

    public Boolean isTwitter() {
        return twitter;
    }

    public TripSettings twitter(Boolean twitter) {
        this.twitter = twitter;
        return this;
    }

    public void setTwitter(Boolean twitter) {
        this.twitter = twitter;
    }

    public Boolean isInstagram() {
        return instagram;
    }

    public TripSettings instagram(Boolean instagram) {
        this.instagram = instagram;
        return this;
    }

    public void setInstagram(Boolean instagram) {
        this.instagram = instagram;
    }

    public Boolean isLocation() {
        return location;
    }

    public TripSettings location(Boolean location) {
        this.location = location;
        return this;
    }

    public void setLocation(Boolean location) {
        this.location = location;
    }

    public Boolean isSync() {
        return sync;
    }

    public TripSettings sync(Boolean sync) {
        this.sync = sync;
        return this;
    }

    public void setSync(Boolean sync) {
        this.sync = sync;
    }

    public Boolean isCheckIn() {
        return checkIn;
    }

    public TripSettings checkIn(Boolean checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    public void setCheckIn(Boolean checkIn) {
        this.checkIn = checkIn;
    }

    public Boolean isCamerRoll() {
        return camerRoll;
    }

    public TripSettings camerRoll(Boolean camerRoll) {
        this.camerRoll = camerRoll;
        return this;
    }

    public void setCamerRoll(Boolean camerRoll) {
        this.camerRoll = camerRoll;
    }

    public Long getCameraRollSyncTime() {
        return cameraRollSyncTime;
    }

    public TripSettings cameraRollSyncTime(Long cameraRollSyncTime) {
        this.cameraRollSyncTime = cameraRollSyncTime;
        return this;
    }

    public void setCameraRollSyncTime(Long cameraRollSyncTime) {
        this.cameraRollSyncTime = cameraRollSyncTime;
    }

    public Long getFacebookSyncTime() {
        return facebookSyncTime;
    }

    public TripSettings facebookSyncTime(Long facebookSyncTime) {
        this.facebookSyncTime = facebookSyncTime;
        return this;
    }

    public void setFacebookSyncTime(Long facebookSyncTime) {
        this.facebookSyncTime = facebookSyncTime;
    }

    public Long getTwitterSinceId() {
        return twitterSinceId;
    }

    public TripSettings twitterSinceId(Long twitterSinceId) {
        this.twitterSinceId = twitterSinceId;
        return this;
    }

    public void setTwitterSinceId(Long twitterSinceId) {
        this.twitterSinceId = twitterSinceId;
    }

    public Long getInstagramSyncTime() {
        return instagramSyncTime;
    }

    public TripSettings instagramSyncTime(Long instagramSyncTime) {
        this.instagramSyncTime = instagramSyncTime;
        return this;
    }

    public void setInstagramSyncTime(Long instagramSyncTime) {
        this.instagramSyncTime = instagramSyncTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TripSettings tripSettings = (TripSettings) o;
        if (tripSettings.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tripSettings.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TripSettings{" +
            "id=" + id +
            ", facebook='" + facebook + "'" +
            ", twitter='" + twitter + "'" +
            ", instagram='" + instagram + "'" +
            ", location='" + location + "'" +
            ", sync='" + sync + "'" +
            ", checkIn='" + checkIn + "'" +
            ", camerRoll='" + camerRoll + "'" +
            ", cameraRollSyncTime='" + cameraRollSyncTime + "'" +
            ", facebookSyncTime='" + facebookSyncTime + "'" +
            ", twitterSinceId='" + twitterSinceId + "'" +
            ", instagramSyncTime='" + instagramSyncTime + "'" +
            '}';
    }
}
