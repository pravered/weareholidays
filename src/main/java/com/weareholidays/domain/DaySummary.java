package com.weareholidays.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DaySummary.
 */
@Entity
@Table(name = "day_summary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DaySummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "twitter")
    private Integer twitter;

    @Column(name = "fb")
    private Integer fb;

    @Column(name = "instagram")
    private Integer instagram;

    @Column(name = "photos")
    private Integer photos;

    @Column(name = "public_photos")
    private Integer publicPhotos;

    @Column(name = "notes")
    private Integer notes;

    @Column(name = "videos")
    private Integer videos;

    @Column(name = "check_ins")
    private Integer checkIns;

    @Column(name = "distance")
    private Double distance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTwitter() {
        return twitter;
    }

    public DaySummary twitter(Integer twitter) {
        this.twitter = twitter;
        return this;
    }

    public void setTwitter(Integer twitter) {
        this.twitter = twitter;
    }

    public Integer getFb() {
        return fb;
    }

    public DaySummary fb(Integer fb) {
        this.fb = fb;
        return this;
    }

    public void setFb(Integer fb) {
        this.fb = fb;
    }

    public Integer getInstagram() {
        return instagram;
    }

    public DaySummary instagram(Integer instagram) {
        this.instagram = instagram;
        return this;
    }

    public void setInstagram(Integer instagram) {
        this.instagram = instagram;
    }

    public Integer getPhotos() {
        return photos;
    }

    public DaySummary photos(Integer photos) {
        this.photos = photos;
        return this;
    }

    public void setPhotos(Integer photos) {
        this.photos = photos;
    }

    public Integer getPublicPhotos() {
        return publicPhotos;
    }

    public DaySummary publicPhotos(Integer publicPhotos) {
        this.publicPhotos = publicPhotos;
        return this;
    }

    public void setPublicPhotos(Integer publicPhotos) {
        this.publicPhotos = publicPhotos;
    }

    public Integer getNotes() {
        return notes;
    }

    public DaySummary notes(Integer notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(Integer notes) {
        this.notes = notes;
    }

    public Integer getVideos() {
        return videos;
    }

    public DaySummary videos(Integer videos) {
        this.videos = videos;
        return this;
    }

    public void setVideos(Integer videos) {
        this.videos = videos;
    }

    public Integer getCheckIns() {
        return checkIns;
    }

    public DaySummary checkIns(Integer checkIns) {
        this.checkIns = checkIns;
        return this;
    }

    public void setCheckIns(Integer checkIns) {
        this.checkIns = checkIns;
    }

    public Double getDistance() {
        return distance;
    }

    public DaySummary distance(Double distance) {
        this.distance = distance;
        return this;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DaySummary daySummary = (DaySummary) o;
        if (daySummary.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, daySummary.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DaySummary{" +
            "id=" + id +
            ", twitter='" + twitter + "'" +
            ", fb='" + fb + "'" +
            ", instagram='" + instagram + "'" +
            ", photos='" + photos + "'" +
            ", publicPhotos='" + publicPhotos + "'" +
            ", notes='" + notes + "'" +
            ", videos='" + videos + "'" +
            ", checkIns='" + checkIns + "'" +
            ", distance='" + distance + "'" +
            '}';
    }
}
