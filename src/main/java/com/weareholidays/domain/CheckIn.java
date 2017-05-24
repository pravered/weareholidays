package com.weareholidays.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CheckIn.
 */
@Entity
@Table(name = "check_in")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CheckIn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "location_lat")
    private Double locationLat;

    @Column(name = "location_long")
    private Double locationLong;

    @Column(name = "name")
    private String name;

    @Column(name = "location_text")
    private String locationText;

    @Column(name = "place_id")
    private String placeId;

    @Column(name = "photo_reference")
    private String photoReference;

    @Column(name = "map_image_local_uri")
    private String mapImageLocalUri;

    @Column(name = "map_image_remote_uri")
    private String mapImageRemoteUri;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLocationLat() {
        return locationLat;
    }

    public CheckIn locationLat(Double locationLat) {
        this.locationLat = locationLat;
        return this;
    }

    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }

    public Double getLocationLong() {
        return locationLong;
    }

    public CheckIn locationLong(Double locationLong) {
        this.locationLong = locationLong;
        return this;
    }

    public void setLocationLong(Double locationLong) {
        this.locationLong = locationLong;
    }

    public String getName() {
        return name;
    }

    public CheckIn name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocationText() {
        return locationText;
    }

    public CheckIn locationText(String locationText) {
        this.locationText = locationText;
        return this;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public String getPlaceId() {
        return placeId;
    }

    public CheckIn placeId(String placeId) {
        this.placeId = placeId;
        return this;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public CheckIn photoReference(String photoReference) {
        this.photoReference = photoReference;
        return this;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getMapImageLocalUri() {
        return mapImageLocalUri;
    }

    public CheckIn mapImageLocalUri(String mapImageLocalUri) {
        this.mapImageLocalUri = mapImageLocalUri;
        return this;
    }

    public void setMapImageLocalUri(String mapImageLocalUri) {
        this.mapImageLocalUri = mapImageLocalUri;
    }

    public String getMapImageRemoteUri() {
        return mapImageRemoteUri;
    }

    public CheckIn mapImageRemoteUri(String mapImageRemoteUri) {
        this.mapImageRemoteUri = mapImageRemoteUri;
        return this;
    }

    public void setMapImageRemoteUri(String mapImageRemoteUri) {
        this.mapImageRemoteUri = mapImageRemoteUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CheckIn checkIn = (CheckIn) o;
        if (checkIn.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, checkIn.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CheckIn{" +
            "id=" + id +
            ", locationLat='" + locationLat + "'" +
            ", locationLong='" + locationLong + "'" +
            ", name='" + name + "'" +
            ", locationText='" + locationText + "'" +
            ", placeId='" + placeId + "'" +
            ", photoReference='" + photoReference + "'" +
            ", mapImageLocalUri='" + mapImageLocalUri + "'" +
            ", mapImageRemoteUri='" + mapImageRemoteUri + "'" +
            '}';
    }
}
