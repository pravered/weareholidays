package com.weareholidays.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Coupon.
 */
@Entity
@Table(name = "coupon")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "object_id")
    private String objectId;

    @Column(name = "code")
    private String code;

    @Column(name = "message")
    private String message;

    @Column(name = "valid_from")
    private Long validFrom;

    @Column(name = "valid_till")
    private Long validTill;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "published_message")
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

    public Coupon objectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCode() {
        return code;
    }

    public Coupon code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public Coupon message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getValidFrom() {
        return validFrom;
    }

    public Coupon validFrom(Long validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Long validFrom) {
        this.validFrom = validFrom;
    }

    public Long getValidTill() {
        return validTill;
    }

    public Coupon validTill(Long validTill) {
        this.validTill = validTill;
        return this;
    }

    public void setValidTill(Long validTill) {
        this.validTill = validTill;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Coupon isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getPublishedMessage() {
        return publishedMessage;
    }

    public Coupon publishedMessage(String publishedMessage) {
        this.publishedMessage = publishedMessage;
        return this;
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
        Coupon coupon = (Coupon) o;
        if (coupon.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, coupon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Coupon{" +
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
