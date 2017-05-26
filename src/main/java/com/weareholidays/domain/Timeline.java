package com.weareholidays.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Timeline.
 */
@Entity
@Table(name = "timeline")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Timeline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "album_content")
    private String albumContent;

    @Column(name = "day_summary_dummy_content")
    private String daySummaryDummyContent;

    @Column(name = "intercity_travel_location_pin")
    private String intercityTravelLocationPin;

    @Column(name = "day_location_pin")
    private String dayLocationPin;

    @Column(name = "check_in_content")
    private String checkInContent;

    @Column(name = "note_content")
    private String noteContent;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "content_time_stamp")
    private Long contentTimeStamp;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "source")
    private String source;

    @Column(name = "third_party_id")
    private String thirdPartyId;

    @Column(name = "day_order")
    private Integer dayOrder;

    @Column(name = "date_in_milli")
    private Long dateInMilli;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Content content;

    @ManyToOne
    private Day day;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlbumContent() {
        return albumContent;
    }

    public Timeline albumContent(String albumContent) {
        this.albumContent = albumContent;
        return this;
    }

    public void setAlbumContent(String albumContent) {
        this.albumContent = albumContent;
    }

    public String getDaySummaryDummyContent() {
        return daySummaryDummyContent;
    }

    public Timeline daySummaryDummyContent(String daySummaryDummyContent) {
        this.daySummaryDummyContent = daySummaryDummyContent;
        return this;
    }

    public void setDaySummaryDummyContent(String daySummaryDummyContent) {
        this.daySummaryDummyContent = daySummaryDummyContent;
    }

    public String getIntercityTravelLocationPin() {
        return intercityTravelLocationPin;
    }

    public Timeline intercityTravelLocationPin(String intercityTravelLocationPin) {
        this.intercityTravelLocationPin = intercityTravelLocationPin;
        return this;
    }

    public void setIntercityTravelLocationPin(String intercityTravelLocationPin) {
        this.intercityTravelLocationPin = intercityTravelLocationPin;
    }

    public String getDayLocationPin() {
        return dayLocationPin;
    }

    public Timeline dayLocationPin(String dayLocationPin) {
        this.dayLocationPin = dayLocationPin;
        return this;
    }

    public void setDayLocationPin(String dayLocationPin) {
        this.dayLocationPin = dayLocationPin;
    }

    public String getCheckInContent() {
        return checkInContent;
    }

    public Timeline checkInContent(String checkInContent) {
        this.checkInContent = checkInContent;
        return this;
    }

    public void setCheckInContent(String checkInContent) {
        this.checkInContent = checkInContent;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public Timeline noteContent(String noteContent) {
        this.noteContent = noteContent;
        return this;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getContentType() {
        return contentType;
    }

    public Timeline contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getContentTimeStamp() {
        return contentTimeStamp;
    }

    public Timeline contentTimeStamp(Long contentTimeStamp) {
        this.contentTimeStamp = contentTimeStamp;
        return this;
    }

    public void setContentTimeStamp(Long contentTimeStamp) {
        this.contentTimeStamp = contentTimeStamp;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public Timeline displayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getSource() {
        return source;
    }

    public Timeline source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public Timeline thirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
        return this;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public Integer getDayOrder() {
        return dayOrder;
    }

    public Timeline dayOrder(Integer dayOrder) {
        this.dayOrder = dayOrder;
        return this;
    }

    public void setDayOrder(Integer dayOrder) {
        this.dayOrder = dayOrder;
    }

    public Long getDateInMilli() {
        return dateInMilli;
    }

    public Timeline dateInMilli(Long dateInMilli) {
        this.dateInMilli = dateInMilli;
        return this;
    }

    public void setDateInMilli(Long dateInMilli) {
        this.dateInMilli = dateInMilli;
    }

    public Content getContent() {
        return content;
    }

    public Timeline content(Content content) {
        this.content = content;
        return this;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Day getDay() {
        return day;
    }

    public Timeline day(Day day) {
        this.day = day;
        return this;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Timeline timeline = (Timeline) o;
        if (timeline.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, timeline.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Timeline{" +
            "id=" + id +
            ", albumContent='" + albumContent + "'" +
            ", daySummaryDummyContent='" + daySummaryDummyContent + "'" +
            ", intercityTravelLocationPin='" + intercityTravelLocationPin + "'" +
            ", dayLocationPin='" + dayLocationPin + "'" +
            ", checkInContent='" + checkInContent + "'" +
            ", noteContent='" + noteContent + "'" +
            ", contentType='" + contentType + "'" +
            ", contentTimeStamp='" + contentTimeStamp + "'" +
            ", displayOrder='" + displayOrder + "'" +
            ", source='" + source + "'" +
            ", thirdPartyId='" + thirdPartyId + "'" +
            ", dayOrder='" + dayOrder + "'" +
            ", dateInMilli='" + dateInMilli + "'" +
            '}';
    }
}
