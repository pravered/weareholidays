package com.weareholidays.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Note.
 */
@Entity
@Table(name = "note")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "location_lat")
    private Double locationLat;

    @Column(name = "location_long")
    private Double locationLong;

    @Column(name = "location_text")
    private String locationText;

    @Column(name = "place_id")
    private String placeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Note content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getLocationLat() {
        return locationLat;
    }

    public Note locationLat(Double locationLat) {
        this.locationLat = locationLat;
        return this;
    }

    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }

    public Double getLocationLong() {
        return locationLong;
    }

    public Note locationLong(Double locationLong) {
        this.locationLong = locationLong;
        return this;
    }

    public void setLocationLong(Double locationLong) {
        this.locationLong = locationLong;
    }

    public String getLocationText() {
        return locationText;
    }

    public Note locationText(String locationText) {
        this.locationText = locationText;
        return this;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public String getPlaceId() {
        return placeId;
    }

    public Note placeId(String placeId) {
        this.placeId = placeId;
        return this;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Note note = (Note) o;
        if (note.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, note.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Note{" +
            "id=" + id +
            ", content='" + content + "'" +
            ", locationLat='" + locationLat + "'" +
            ", locationLong='" + locationLong + "'" +
            ", locationText='" + locationText + "'" +
            ", placeId='" + placeId + "'" +
            '}';
    }
}
