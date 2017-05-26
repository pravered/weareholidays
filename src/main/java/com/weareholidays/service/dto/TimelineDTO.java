package com.weareholidays.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Timeline entity.
 */
public class TimelineDTO implements Serializable {

    private Long id;

    private String albumContent;

    private String daySummaryDummyContent;

    private String intercityTravelLocationPin;

    private String dayLocationPin;

    private String checkInContent;

    private String noteContent;

    private String contentType;

    private Long contentTimeStamp;

    private Integer displayOrder;

    private String source;

    private String thirdPartyId;

    private Integer dayOrder;

    private Long dateInMilli;

    private ContentDTO content;

    private Long dayId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getAlbumContent() {
        return albumContent;
    }

    public void setAlbumContent(String albumContent) {
        this.albumContent = albumContent;
    }
    public String getDaySummaryDummyContent() {
        return daySummaryDummyContent;
    }

    public void setDaySummaryDummyContent(String daySummaryDummyContent) {
        this.daySummaryDummyContent = daySummaryDummyContent;
    }
    public String getIntercityTravelLocationPin() {
        return intercityTravelLocationPin;
    }

    public void setIntercityTravelLocationPin(String intercityTravelLocationPin) {
        this.intercityTravelLocationPin = intercityTravelLocationPin;
    }
    public String getDayLocationPin() {
        return dayLocationPin;
    }

    public void setDayLocationPin(String dayLocationPin) {
        this.dayLocationPin = dayLocationPin;
    }
    public String getCheckInContent() {
        return checkInContent;
    }

    public void setCheckInContent(String checkInContent) {
        this.checkInContent = checkInContent;
    }
    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public Long getContentTimeStamp() {
        return contentTimeStamp;
    }

    public void setContentTimeStamp(Long contentTimeStamp) {
        this.contentTimeStamp = contentTimeStamp;
    }
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }
    public Integer getDayOrder() {
        return dayOrder;
    }

    public void setDayOrder(Integer dayOrder) {
        this.dayOrder = dayOrder;
    }
    public Long getDateInMilli() {
        return dateInMilli;
    }

    public void setDateInMilli(Long dateInMilli) {
        this.dateInMilli = dateInMilli;
    }

    public ContentDTO getContent() {
        return content;
    }

    public void setContent(ContentDTO content) {
        this.content = content;
    }

    public Long getDayId() {
        return dayId;
    }

    public void setDayId(Long dayId) {
        this.dayId = dayId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TimelineDTO timelineDTO = (TimelineDTO) o;

        if ( ! Objects.equals(id, timelineDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TimelineDTO{" +
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
