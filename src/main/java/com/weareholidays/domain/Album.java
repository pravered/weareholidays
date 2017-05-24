package com.weareholidays.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Album.
 */
@Entity
@Table(name = "album")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "media_count")
    private Integer mediaCount;

    @Column(name = "public_media_count")
    private Integer publicMediaCount;

    @Column(name = "location_lat")
    private Double locationLat;

    @Column(name = "location_long")
    private Double locationLong;

    @Column(name = "location_text")
    private String locationText;

    @Column(name = "start_time")
    private Long startTime;

    @Column(name = "end_time")
    private Long endTime;

    @Column(name = "source")
    private String source;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "album")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Media> media = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Album content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMediaCount() {
        return mediaCount;
    }

    public Album mediaCount(Integer mediaCount) {
        this.mediaCount = mediaCount;
        return this;
    }

    public void setMediaCount(Integer mediaCount) {
        this.mediaCount = mediaCount;
    }

    public Integer getPublicMediaCount() {
        return publicMediaCount;
    }

    public Album publicMediaCount(Integer publicMediaCount) {
        this.publicMediaCount = publicMediaCount;
        return this;
    }

    public void setPublicMediaCount(Integer publicMediaCount) {
        this.publicMediaCount = publicMediaCount;
    }

    public Double getLocationLat() {
        return locationLat;
    }

    public Album locationLat(Double locationLat) {
        this.locationLat = locationLat;
        return this;
    }

    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }

    public Double getLocationLong() {
        return locationLong;
    }

    public Album locationLong(Double locationLong) {
        this.locationLong = locationLong;
        return this;
    }

    public void setLocationLong(Double locationLong) {
        this.locationLong = locationLong;
    }

    public String getLocationText() {
        return locationText;
    }

    public Album locationText(String locationText) {
        this.locationText = locationText;
        return this;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Album startTime(Long startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public Album endTime(Long endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getSource() {
        return source;
    }

    public Album source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCategory() {
        return category;
    }

    public Album category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Media> getMedia() {
        return media;
    }

    public Album media(Set<Media> media) {
        this.media = media;
        return this;
    }

    public Album addMedia(Media media) {
        this.media.add(media);
        media.setAlbum(this);
        return this;
    }

    public Album removeMedia(Media media) {
        this.media.remove(media);
        media.setAlbum(null);
        return this;
    }

    public void setMedia(Set<Media> media) {
        this.media = media;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Album album = (Album) o;
        if (album.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, album.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Album{" +
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
