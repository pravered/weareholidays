package com.weareholidays.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Day entity.
 */
public class DayDTO implements Serializable {

    private Long id;

    private String name;

    private Integer displayOrder;

    private Long startTime;

    private Long endTime;

    private Double locationLat;

    private Double locationLong;

    private String city;

    private String country;

    private DaySummaryDTO daySummary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
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
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public DaySummaryDTO getDaySummary() {
        return daySummary;
    }

    public void setDaySummary(DaySummaryDTO daySummary) {
        this.daySummary = daySummary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DayDTO dayDTO = (DayDTO) o;

        if ( ! Objects.equals(id, dayDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DayDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", displayOrder='" + displayOrder + "'" +
            ", startTime='" + startTime + "'" +
            ", endTime='" + endTime + "'" +
            ", locationLat='" + locationLat + "'" +
            ", locationLong='" + locationLong + "'" +
            ", city='" + city + "'" +
            ", country='" + country + "'" +
            '}';
    }
}
