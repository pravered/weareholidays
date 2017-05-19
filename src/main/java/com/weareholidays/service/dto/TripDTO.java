package com.weareholidays.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Trip entity.
 */
public class TripDTO implements Serializable {

    private Long id;

    private String name;

    private Double startLocationLat;

    private Double startLocationLong;

    private Double endLocationLat;

    private Double endLocationLong;

    private Long startTime;

    private Long endTime;

    private Boolean isUploaded;

    private Boolean isFinished;

    private Boolean isPublished;

    private Long publishedTime;

    private Boolean isDeleted;

    private String featureImageLocalUri;

    private String featureImageRemoteUri;

    private Integer noOfDays;

    private String createdAt;

    private Integer viewCount;

    private String secretKey;

    private Integer featured;

    private Boolean isHidden;

    private TripSummaryDTO tripSummary;

    private TripSettingsDTO tripSettings;

    private Long userId;

    private String userLogin;

    private Long couponId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Double getStartLocationLat() {
        return startLocationLat;
    }

    public void setStartLocationLat(Double startLocationLat) {
        this.startLocationLat = startLocationLat;
    }
    public Double getStartLocationLong() {
        return startLocationLong;
    }

    public void setStartLocationLong(Double startLocationLong) {
        this.startLocationLong = startLocationLong;
    }
    public Double getEndLocationLat() {
        return endLocationLat;
    }

    public void setEndLocationLat(Double endLocationLat) {
        this.endLocationLat = endLocationLat;
    }
    public Double getEndLocationLong() {
        return endLocationLong;
    }

    public void setEndLocationLong(Double endLocationLong) {
        this.endLocationLong = endLocationLong;
    }
    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
    public Boolean getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(Boolean isUploaded) {
        this.isUploaded = isUploaded;
    }
    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }
    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }
    public Long getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(Long publishedTime) {
        this.publishedTime = publishedTime;
    }
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    public String getFeatureImageLocalUri() {
        return featureImageLocalUri;
    }

    public void setFeatureImageLocalUri(String featureImageLocalUri) {
        this.featureImageLocalUri = featureImageLocalUri;
    }
    public String getFeatureImageRemoteUri() {
        return featureImageRemoteUri;
    }

    public void setFeatureImageRemoteUri(String featureImageRemoteUri) {
        this.featureImageRemoteUri = featureImageRemoteUri;
    }
    public Integer getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    public Integer getFeatured() {
        return featured;
    }

    public void setFeatured(Integer featured) {
        this.featured = featured;
    }
    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public TripSummaryDTO getTripSummary() {
        return tripSummary;
    }

    public void setTripSummary(TripSummaryDTO tripSummary) {
        this.tripSummary = tripSummary;
    }

    public TripSettingsDTO getTripSettings() {
        return tripSettings;
    }

    public void setTripSettings(TripSettingsDTO tripSettings) {
        this.tripSettings = tripSettings;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TripDTO tripDTO = (TripDTO) o;

        if ( ! Objects.equals(id, tripDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TripDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", startLocationLat='" + startLocationLat + "'" +
            ", startLocationLong='" + startLocationLong + "'" +
            ", endLocationLat='" + endLocationLat + "'" +
            ", endLocationLong='" + endLocationLong + "'" +
            ", startTime='" + startTime + "'" +
            ", endTime='" + endTime + "'" +
            ", isUploaded='" + isUploaded + "'" +
            ", isFinished='" + isFinished + "'" +
            ", isPublished='" + isPublished + "'" +
            ", publishedTime='" + publishedTime + "'" +
            ", isDeleted='" + isDeleted + "'" +
            ", featureImageLocalUri='" + featureImageLocalUri + "'" +
            ", featureImageRemoteUri='" + featureImageRemoteUri + "'" +
            ", noOfDays='" + noOfDays + "'" +
            ", createdAt='" + createdAt + "'" +
            ", viewCount='" + viewCount + "'" +
            ", secretKey='" + secretKey + "'" +
            ", featured='" + featured + "'" +
            ", isHidden='" + isHidden + "'" +
            '}';
    }
}
