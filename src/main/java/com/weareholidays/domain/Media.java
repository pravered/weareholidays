package com.weareholidays.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Media.
 */
@Entity
@Table(name = "media")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Media implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "content_local_url")
    private String contentLocalUrl;

    @Column(name = "content_remote_url")
    private String contentRemoteUrl;

    @Column(name = "caption")
    private String caption;

    @Column(name = "location_lat")
    private Double locationLat;

    @Column(name = "location_long")
    private Double locationLong;

    @Column(name = "is_private")
    private Boolean isPrivate;

    @Column(name = "address")
    private String address;

    @Column(name = "content_creation_time")
    private Long contentCreationTime;

    @Column(name = "content_size")
    private Long contentSize;

    @Column(name = "media_width")
    private Integer mediaWidth;

    @Column(name = "media_height")
    private Integer mediaHeight;

    @Column(name = "third_party_id")
    private String thirdPartyId;

    @Column(name = "third_party_url")
    private String thirdPartyUrl;

    @Column(name = "media_source")
    private String mediaSource;

    @Column(name = "fetching_address")
    private Boolean fetchingAddress;

    @ManyToOne
    private Album album;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentLocalUrl() {
        return contentLocalUrl;
    }

    public Media contentLocalUrl(String contentLocalUrl) {
        this.contentLocalUrl = contentLocalUrl;
        return this;
    }

    public void setContentLocalUrl(String contentLocalUrl) {
        this.contentLocalUrl = contentLocalUrl;
    }

    public String getContentRemoteUrl() {
        return contentRemoteUrl;
    }

    public Media contentRemoteUrl(String contentRemoteUrl) {
        this.contentRemoteUrl = contentRemoteUrl;
        return this;
    }

    public void setContentRemoteUrl(String contentRemoteUrl) {
        this.contentRemoteUrl = contentRemoteUrl;
    }

    public String getCaption() {
        return caption;
    }

    public Media caption(String caption) {
        this.caption = caption;
        return this;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Double getLocationLat() {
        return locationLat;
    }

    public Media locationLat(Double locationLat) {
        this.locationLat = locationLat;
        return this;
    }

    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }

    public Double getLocationLong() {
        return locationLong;
    }

    public Media locationLong(Double locationLong) {
        this.locationLong = locationLong;
        return this;
    }

    public void setLocationLong(Double locationLong) {
        this.locationLong = locationLong;
    }

    public Boolean isIsPrivate() {
        return isPrivate;
    }

    public Media isPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
        return this;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getAddress() {
        return address;
    }

    public Media address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getContentCreationTime() {
        return contentCreationTime;
    }

    public Media contentCreationTime(Long contentCreationTime) {
        this.contentCreationTime = contentCreationTime;
        return this;
    }

    public void setContentCreationTime(Long contentCreationTime) {
        this.contentCreationTime = contentCreationTime;
    }

    public Long getContentSize() {
        return contentSize;
    }

    public Media contentSize(Long contentSize) {
        this.contentSize = contentSize;
        return this;
    }

    public void setContentSize(Long contentSize) {
        this.contentSize = contentSize;
    }

    public Integer getMediaWidth() {
        return mediaWidth;
    }

    public Media mediaWidth(Integer mediaWidth) {
        this.mediaWidth = mediaWidth;
        return this;
    }

    public void setMediaWidth(Integer mediaWidth) {
        this.mediaWidth = mediaWidth;
    }

    public Integer getMediaHeight() {
        return mediaHeight;
    }

    public Media mediaHeight(Integer mediaHeight) {
        this.mediaHeight = mediaHeight;
        return this;
    }

    public void setMediaHeight(Integer mediaHeight) {
        this.mediaHeight = mediaHeight;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public Media thirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
        return this;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getThirdPartyUrl() {
        return thirdPartyUrl;
    }

    public Media thirdPartyUrl(String thirdPartyUrl) {
        this.thirdPartyUrl = thirdPartyUrl;
        return this;
    }

    public void setThirdPartyUrl(String thirdPartyUrl) {
        this.thirdPartyUrl = thirdPartyUrl;
    }

    public String getMediaSource() {
        return mediaSource;
    }

    public Media mediaSource(String mediaSource) {
        this.mediaSource = mediaSource;
        return this;
    }

    public void setMediaSource(String mediaSource) {
        this.mediaSource = mediaSource;
    }

    public Boolean isFetchingAddress() {
        return fetchingAddress;
    }

    public Media fetchingAddress(Boolean fetchingAddress) {
        this.fetchingAddress = fetchingAddress;
        return this;
    }

    public void setFetchingAddress(Boolean fetchingAddress) {
        this.fetchingAddress = fetchingAddress;
    }

    public Album getAlbum() {
        return album;
    }

    public Media album(Album album) {
        this.album = album;
        return this;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Media media = (Media) o;
        if (media.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, media.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Media{" +
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
