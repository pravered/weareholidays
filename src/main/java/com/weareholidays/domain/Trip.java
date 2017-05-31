package com.weareholidays.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Trip.
 */
@Entity
@Table(name = "trip")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Trip implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_location_lat")
    private Double startLocationLat;

    @Column(name = "start_location_long")
    private Double startLocationLong;

    @Column(name = "end_location_lat")
    private Double endLocationLat;

    @Column(name = "end_location_long")
    private Double endLocationLong;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "is_uploaded")
    private Boolean isUploaded;

    @Column(name = "is_finished")
    private Boolean isFinished;

    @Column(name = "is_published")
    private Boolean isPublished;

    @Column(name = "published_time")
    private Date publishedTime;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "feature_image_local_uri")
    private String featureImageLocalUri;

    @Column(name = "feature_image_remote_uri")
    private String featureImageRemoteUri;

    @Column(name = "no_of_days")
    private Integer noOfDays;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "secret_key")
    private String secretKey;

    @Column(name = "featured")
    private Integer featured;

    @Column(name = "is_hidden")
    private Boolean isHidden;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private TripSummary tripSummary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private TripSettings tripSettings;

    @OneToMany
    @JoinTable(name = "trip_id")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Day> days = new HashSet<>();

    @OneToMany(mappedBy = "trip")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TripPeople> tripPeople = new HashSet<>();

    @ManyToOne
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Coupon coupon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Trip name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getStartLocationLat() {
        return startLocationLat;
    }

    public Trip startLocationLat(Double startLocationLat) {
        this.startLocationLat = startLocationLat;
        return this;
    }

    public void setStartLocationLat(Double startLocationLat) {
        this.startLocationLat = startLocationLat;
    }

    public Double getStartLocationLong() {
        return startLocationLong;
    }

    public Trip startLocationLong(Double startLocationLong) {
        this.startLocationLong = startLocationLong;
        return this;
    }

    public void setStartLocationLong(Double startLocationLong) {
        this.startLocationLong = startLocationLong;
    }

    public Double getEndLocationLat() {
        return endLocationLat;
    }

    public Trip endLocationLat(Double endLocationLat) {
        this.endLocationLat = endLocationLat;
        return this;
    }

    public void setEndLocationLat(Double endLocationLat) {
        this.endLocationLat = endLocationLat;
    }

    public Double getEndLocationLong() {
        return endLocationLong;
    }

    public Trip endLocationLong(Double endLocationLong) {
        this.endLocationLong = endLocationLong;
        return this;
    }

    public void setEndLocationLong(Double endLocationLong) {
        this.endLocationLong = endLocationLong;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Trip startTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Trip endTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean isIsUploaded() {
        return isUploaded;
    }

    public Trip isUploaded(Boolean isUploaded) {
        this.isUploaded = isUploaded;
        return this;
    }

    public void setIsUploaded(Boolean isUploaded) {
        this.isUploaded = isUploaded;
    }

    public Boolean isIsFinished() {
        return isFinished;
    }

    public Trip isFinished(Boolean isFinished) {
        this.isFinished = isFinished;
        return this;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Boolean isIsPublished() {
        return isPublished;
    }

    public Trip isPublished(Boolean isPublished) {
        this.isPublished = isPublished;
        return this;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public Date getPublishedTime() {
        return publishedTime;
    }

    public Trip publishedTime(Date publishedTime) {
        this.publishedTime = publishedTime;
        return this;
    }

    public void setPublishedTime(Date publishedTime) {
        this.publishedTime = publishedTime;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public Trip isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFeatureImageLocalUri() {
        return featureImageLocalUri;
    }

    public Trip featureImageLocalUri(String featureImageLocalUri) {
        this.featureImageLocalUri = featureImageLocalUri;
        return this;
    }

    public void setFeatureImageLocalUri(String featureImageLocalUri) {
        this.featureImageLocalUri = featureImageLocalUri;
    }

    public String getFeatureImageRemoteUri() {
        return featureImageRemoteUri;
    }

    public Trip featureImageRemoteUri(String featureImageRemoteUri) {
        this.featureImageRemoteUri = featureImageRemoteUri;
        return this;
    }

    public void setFeatureImageRemoteUri(String featureImageRemoteUri) {
        this.featureImageRemoteUri = featureImageRemoteUri;
    }

    public Integer getNoOfDays() {
        return noOfDays;
    }

    public Trip noOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
        return this;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Trip createdAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public Trip viewCount(Integer viewCount) {
        this.viewCount = viewCount;
        return this;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public Trip secretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getFeatured() {
        return featured;
    }

    public Trip featured(Integer featured) {
        this.featured = featured;
        return this;
    }

    public void setFeatured(Integer featured) {
        this.featured = featured;
    }

    public Boolean isIsHidden() {
        return isHidden;
    }

    public Trip isHidden(Boolean isHidden) {
        this.isHidden = isHidden;
        return this;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public TripSummary getTripSummary() {
        return tripSummary;
    }

    public Trip tripSummary(TripSummary tripSummary) {
        this.tripSummary = tripSummary;
        return this;
    }

    public void setTripSummary(TripSummary tripSummary) {
        this.tripSummary = tripSummary;
    }

    public TripSettings getTripSettings() {
        return tripSettings;
    }

    public Trip tripSettings(TripSettings tripSettings) {
        this.tripSettings = tripSettings;
        return this;
    }

    public void setTripSettings(TripSettings tripSettings) {
        this.tripSettings = tripSettings;
    }

    public Set<Day> getDays() {
        return days;
    }

    public Trip days(Set<Day> days) {
        this.days = days;
        return this;
    }

    public Trip addDay(Day day) {
        this.days.add(day);
        day.setTrip(this);
        return this;
    }

    public Trip removeDay(Day day) {
        this.days.remove(day);
        day.setTrip(null);
        return this;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }

    public Set<TripPeople> getTripPeople() {
        return tripPeople;
    }

    public Trip tripPeople(Set<TripPeople> tripPeople) {
        this.tripPeople = tripPeople;
        return this;
    }

    public Trip addTripPeople(TripPeople tripPeople) {
        this.tripPeople.add(tripPeople);
        tripPeople.setTrip(this);
        return this;
    }

    public Trip removeTripPeople(TripPeople tripPeople) {
        this.tripPeople.remove(tripPeople);
        tripPeople.setTrip(null);
        return this;
    }

    public void setTripPeople(Set<TripPeople> tripPeople) {
        this.tripPeople = tripPeople;
    }

    public User getUser() {
        return user;
    }

    public Trip user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public Trip coupon(Coupon coupon) {
        this.coupon = coupon;
        return this;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trip trip = (Trip) o;
        if (trip.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, trip.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Trip{" +
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
