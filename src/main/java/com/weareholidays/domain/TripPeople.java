package com.weareholidays.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TripPeople.
 */
@Entity
@Table(name = "trip_people")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TripPeople implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "phone_book_type")
    private String phoneBookType;

    @Column(name = "facebook_type")
    private String facebookType;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "image_local_uri")
    private String imageLocalUri;

    @Column(name = "image_remote_uri")
    private String imageRemoteUri;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "in_trip")
    private Boolean inTrip;

    @Column(name = "identifier")
    private String identifier;

    @ManyToOne
    private Trip trip;

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
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

    public TripPeople phoneBookType(String phoneBookType) {
        this.phoneBookType = phoneBookType;
        return this;
    }

    public void setPhoneBookType(String phoneBookType) {
        this.phoneBookType = phoneBookType;
    }

    public String getFacebookType() {
        return facebookType;
    }

    public TripPeople facebookType(String facebookType) {
        this.facebookType = facebookType;
        return this;
    }

    public void setFacebookType(String facebookType) {
        this.facebookType = facebookType;
    }

    public String getName() {
        return name;
    }

    public TripPeople name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public TripPeople email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageLocalUri() {
        return imageLocalUri;
    }

    public TripPeople imageLocalUri(String imageLocalUri) {
        this.imageLocalUri = imageLocalUri;
        return this;
    }

    public void setImageLocalUri(String imageLocalUri) {
        this.imageLocalUri = imageLocalUri;
    }

    public String getImageRemoteUri() {
        return imageRemoteUri;
    }

    public TripPeople imageRemoteUri(String imageRemoteUri) {
        this.imageRemoteUri = imageRemoteUri;
        return this;
    }

    public void setImageRemoteUri(String imageRemoteUri) {
        this.imageRemoteUri = imageRemoteUri;
    }

    public String getType() {
        return type;
    }

    public TripPeople type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isInTrip() {
        return inTrip;
    }

    public TripPeople inTrip(Boolean inTrip) {
        this.inTrip = inTrip;
        return this;
    }

    public void setInTrip(Boolean inTrip) {
        this.inTrip = inTrip;
    }

    public String getIdentifier() {
        return identifier;
    }

    public TripPeople identifier(String identifier) {
        this.identifier = identifier;
        return this;
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
        TripPeople tripPeople = (TripPeople) o;
        if (tripPeople.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tripPeople.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TripPeople{" +
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
