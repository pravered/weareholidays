package com.weareholidays.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TripPeople entity.
 */
public class TripPeopleDTO implements Serializable {

    private Long id;

    private String phoneBookType;

    private String facebookType;

    private String name;

    private String email;

    private String imageLocalUri;

    private String imageRemoteUri;

    private String type;

    private Boolean inTrip;

    private String identifier;

    private Long tripId;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getPhoneBookType() {
        return phoneBookType;
    }

    public void setPhoneBookType(String phoneBookType) {
        this.phoneBookType = phoneBookType;
    }
    public String getFacebookType() {
        return facebookType;
    }

    public void setFacebookType(String facebookType) {
        this.facebookType = facebookType;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getImageLocalUri() {
        return imageLocalUri;
    }

    public void setImageLocalUri(String imageLocalUri) {
        this.imageLocalUri = imageLocalUri;
    }
    public String getImageRemoteUri() {
        return imageRemoteUri;
    }

    public void setImageRemoteUri(String imageRemoteUri) {
        this.imageRemoteUri = imageRemoteUri;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Boolean getInTrip() {
        return inTrip;
    }

    public void setInTrip(Boolean inTrip) {
        this.inTrip = inTrip;
    }
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TripPeopleDTO tripPeopleDTO = (TripPeopleDTO) o;

        if ( ! Objects.equals(id, tripPeopleDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TripPeopleDTO{" +
            "id=" + id +
            ", phoneBookType='" + phoneBookType + "'" +
            ", facebookType='" + facebookType + "'" +
            ", name='" + name + "'" +
            ", email='" + email + "'" +
            ", imageLocalUri='" + imageLocalUri + "'" +
            ", imageRemoteUri='" + imageRemoteUri + "'" +
            ", type='" + type + "'" +
            ", inTrip='" + inTrip + "'" +
            ", identifier='" + identifier + "'" +
            '}';
    }
}
