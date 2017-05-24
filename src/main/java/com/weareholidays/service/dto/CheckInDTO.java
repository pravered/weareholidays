package com.weareholidays.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CheckIn entity.
 */
public class CheckInDTO implements Serializable {

    private Long id;

    private Double locationLat;

    private Double locationLong;

    private String name;

    private String locationText;

    private String placeId;

    private String photoReference;

    private String mapImageLocalUri;

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

    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }
    public Double getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(Double locationLong) {
        this.locationLong = locationLong;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }
    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }
    public String getMapImageLocalUri() {
        return mapImageLocalUri;
    }

    public void setMapImageLocalUri(String mapImageLocalUri) {
        this.mapImageLocalUri = mapImageLocalUri;
    }
    public String getMapImageRemoteUri() {
        return mapImageRemoteUri;
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

        CheckInDTO checkInDTO = (CheckInDTO) o;

        if ( ! Objects.equals(id, checkInDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CheckInDTO{" +
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
