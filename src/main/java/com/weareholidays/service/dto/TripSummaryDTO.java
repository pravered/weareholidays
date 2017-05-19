package com.weareholidays.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TripSummary entity.
 */
public class TripSummaryDTO implements Serializable {

    private Long id;

    private Integer twitter;

    private Integer fb;

    private Integer instagram;

    private Integer photos;

    private Integer publicPhotos;

    private Integer notes;

    private Integer videos;

    private Integer checkIns;

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

    public void setTwitter(Integer twitter) {
        this.twitter = twitter;
    }
    public Integer getFb() {
        return fb;
    }

    public void setFb(Integer fb) {
        this.fb = fb;
    }
    public Integer getInstagram() {
        return instagram;
    }

    public void setInstagram(Integer instagram) {
        this.instagram = instagram;
    }
    public Integer getPhotos() {
        return photos;
    }

    public void setPhotos(Integer photos) {
        this.photos = photos;
    }
    public Integer getPublicPhotos() {
        return publicPhotos;
    }

    public void setPublicPhotos(Integer publicPhotos) {
        this.publicPhotos = publicPhotos;
    }
    public Integer getNotes() {
        return notes;
    }

    public void setNotes(Integer notes) {
        this.notes = notes;
    }
    public Integer getVideos() {
        return videos;
    }

    public void setVideos(Integer videos) {
        this.videos = videos;
    }
    public Integer getCheckIns() {
        return checkIns;
    }

    public void setCheckIns(Integer checkIns) {
        this.checkIns = checkIns;
    }
    public Double getDistance() {
        return distance;
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

        TripSummaryDTO tripSummaryDTO = (TripSummaryDTO) o;

        if ( ! Objects.equals(id, tripSummaryDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TripSummaryDTO{" +
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
