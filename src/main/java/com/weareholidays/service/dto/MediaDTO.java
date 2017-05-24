package com.weareholidays.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Media entity.
 */
public class MediaDTO implements Serializable {

    private Long id;

    private String contentLocalUrl;

    private String contentRemoteUrl;

    private String caption;

    private Double locationLat;

    private Double locationLong;

    private Boolean isPrivate;

    private String address;

    private Long contentCreationTime;

    private Long contentSize;

    private Integer mediaWidth;

    private Integer mediaHeight;

    private String thirdPartyId;

    private String thirdPartyUrl;

    private String mediaSource;

    private Boolean fetchingAddress;

    private Long albumId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getContentLocalUrl() {
        return contentLocalUrl;
    }

    public void setContentLocalUrl(String contentLocalUrl) {
        this.contentLocalUrl = contentLocalUrl;
    }
    public String getContentRemoteUrl() {
        return contentRemoteUrl;
    }

    public void setContentRemoteUrl(String contentRemoteUrl) {
        this.contentRemoteUrl = contentRemoteUrl;
    }
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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
    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public Long getContentCreationTime() {
        return contentCreationTime;
    }

    public void setContentCreationTime(Long contentCreationTime) {
        this.contentCreationTime = contentCreationTime;
    }
    public Long getContentSize() {
        return contentSize;
    }

    public void setContentSize(Long contentSize) {
        this.contentSize = contentSize;
    }
    public Integer getMediaWidth() {
        return mediaWidth;
    }

    public void setMediaWidth(Integer mediaWidth) {
        this.mediaWidth = mediaWidth;
    }
    public Integer getMediaHeight() {
        return mediaHeight;
    }

    public void setMediaHeight(Integer mediaHeight) {
        this.mediaHeight = mediaHeight;
    }
    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }
    public String getThirdPartyUrl() {
        return thirdPartyUrl;
    }

    public void setThirdPartyUrl(String thirdPartyUrl) {
        this.thirdPartyUrl = thirdPartyUrl;
    }
    public String getMediaSource() {
        return mediaSource;
    }

    public void setMediaSource(String mediaSource) {
        this.mediaSource = mediaSource;
    }
    public Boolean getFetchingAddress() {
        return fetchingAddress;
    }

    public void setFetchingAddress(Boolean fetchingAddress) {
        this.fetchingAddress = fetchingAddress;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MediaDTO mediaDTO = (MediaDTO) o;

        if ( ! Objects.equals(id, mediaDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MediaDTO{" +
            "id=" + id +
            ", contentLocalUrl='" + contentLocalUrl + "'" +
            ", contentRemoteUrl='" + contentRemoteUrl + "'" +
            ", caption='" + caption + "'" +
            ", locationLat='" + locationLat + "'" +
            ", locationLong='" + locationLong + "'" +
            ", isPrivate='" + isPrivate + "'" +
            ", address='" + address + "'" +
            ", contentCreationTime='" + contentCreationTime + "'" +
            ", contentSize='" + contentSize + "'" +
            ", mediaWidth='" + mediaWidth + "'" +
            ", mediaHeight='" + mediaHeight + "'" +
            ", thirdPartyId='" + thirdPartyId + "'" +
            ", thirdPartyUrl='" + thirdPartyUrl + "'" +
            ", mediaSource='" + mediaSource + "'" +
            ", fetchingAddress='" + fetchingAddress + "'" +
            '}';
    }
}
