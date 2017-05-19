package com.weareholidays.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TripSettings entity.
 */
public class TripSettingsDTO implements Serializable {

    private Long id;

    private Boolean facebook;

    private Boolean twitter;

    private Boolean instagram;

    private Boolean location;

    private Boolean sync;

    private Boolean checkIn;

    private Boolean camerRoll;

    private Long cameraRollSyncTime;

    private Long facebookSyncTime;

    private Long twitterSinceId;

    private Long instagramSyncTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getFacebook() {
        return facebook;
    }

    public void setFacebook(Boolean facebook) {
        this.facebook = facebook;
    }
    public Boolean getTwitter() {
        return twitter;
    }

    public void setTwitter(Boolean twitter) {
        this.twitter = twitter;
    }
    public Boolean getInstagram() {
        return instagram;
    }

    public void setInstagram(Boolean instagram) {
        this.instagram = instagram;
    }
    public Boolean getLocation() {
        return location;
    }

    public void setLocation(Boolean location) {
        this.location = location;
    }
    public Boolean getSync() {
        return sync;
    }

    public void setSync(Boolean sync) {
        this.sync = sync;
    }
    public Boolean getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Boolean checkIn) {
        this.checkIn = checkIn;
    }
    public Boolean getCamerRoll() {
        return camerRoll;
    }

    public void setCamerRoll(Boolean camerRoll) {
        this.camerRoll = camerRoll;
    }
    public Long getCameraRollSyncTime() {
        return cameraRollSyncTime;
    }

    public void setCameraRollSyncTime(Long cameraRollSyncTime) {
        this.cameraRollSyncTime = cameraRollSyncTime;
    }
    public Long getFacebookSyncTime() {
        return facebookSyncTime;
    }

    public void setFacebookSyncTime(Long facebookSyncTime) {
        this.facebookSyncTime = facebookSyncTime;
    }
    public Long getTwitterSinceId() {
        return twitterSinceId;
    }

    public void setTwitterSinceId(Long twitterSinceId) {
        this.twitterSinceId = twitterSinceId;
    }
    public Long getInstagramSyncTime() {
        return instagramSyncTime;
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

        TripSettingsDTO tripSettingsDTO = (TripSettingsDTO) o;

        if ( ! Objects.equals(id, tripSettingsDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TripSettingsDTO{" +
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
