package com.weareholidays.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Album entity.
 */
public class AlbumDTO implements Serializable {

    private Long id;

    private String content;

    private Integer mediaCount;

    private Integer publicMediaCount;

    private Double locationLat;

    private Double locationLong;

    private String locationText;

    private Long startTime;

    private Long endTime;

    private String source;

    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Integer getMediaCount() {
        return mediaCount;
    }

    public void setMediaCount(Integer mediaCount) {
        this.mediaCount = mediaCount;
    }
    public Integer getPublicMediaCount() {
        return publicMediaCount;
    }

    public void setPublicMediaCount(Integer publicMediaCount) {
        this.publicMediaCount = publicMediaCount;
    }
    public Double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }
    public Double getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(Double locationLong) {
        this.locationLong = locationLong;
    }
    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
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
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlbumDTO albumDTO = (AlbumDTO) o;

        if ( ! Objects.equals(id, albumDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlbumDTO{" +
            "id=" + id +
            ", content='" + content + "'" +
            ", mediaCount='" + mediaCount + "'" +
            ", publicMediaCount='" + publicMediaCount + "'" +
            ", locationLat='" + locationLat + "'" +
            ", locationLong='" + locationLong + "'" +
            ", locationText='" + locationText + "'" +
            ", startTime='" + startTime + "'" +
            ", endTime='" + endTime + "'" +
            ", source='" + source + "'" +
            ", category='" + category + "'" +
            '}';
    }
}
