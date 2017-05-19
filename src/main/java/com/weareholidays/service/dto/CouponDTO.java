package com.weareholidays.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Coupon entity.
 */
public class CouponDTO implements Serializable {

    private Long id;

    private String objectId;

    private String code;

    private String message;

    private Long validFrom;

    private Long validTill;

    private Boolean isActive;

    private String publishedMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public Long getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Long validFrom) {
        this.validFrom = validFrom;
    }
    public Long getValidTill() {
        return validTill;
    }

    public void setValidTill(Long validTill) {
        this.validTill = validTill;
    }
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    public String getPublishedMessage() {
        return publishedMessage;
    }

    public void setPublishedMessage(String publishedMessage) {
        this.publishedMessage = publishedMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CouponDTO couponDTO = (CouponDTO) o;

        if ( ! Objects.equals(id, couponDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CouponDTO{" +
            "id=" + id +
            ", objectId='" + objectId + "'" +
            ", code='" + code + "'" +
            ", message='" + message + "'" +
            ", validFrom='" + validFrom + "'" +
            ", validTill='" + validTill + "'" +
            ", isActive='" + isActive + "'" +
            ", publishedMessage='" + publishedMessage + "'" +
            '}';
    }
}
