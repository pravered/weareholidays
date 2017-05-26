package com.weareholidays.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Content entity.
 */
public class ContentDTO implements Serializable {

    private Long id;

    private MediaDTO media;

    private AlbumDTO album;

    private CheckInDTO checkIn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MediaDTO getMedia() {
        return media;
    }

    public void setMedia(MediaDTO media) {
        this.media = media;
    }

    public AlbumDTO getAlbum() {
        return album;
    }

    public void setAlbum(AlbumDTO album) {
        this.album = album;
    }

    public CheckInDTO getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(CheckInDTO checkIn) {
        this.checkIn = checkIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContentDTO contentDTO = (ContentDTO) o;

        if ( ! Objects.equals(id, contentDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ContentDTO{" +
            "id=" + id +
            '}';
    }
}
