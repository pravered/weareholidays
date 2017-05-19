package com.weareholidays.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Day.
 */
@Entity
@Table(name = "day")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Day implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "start_time")
    private Long startTime;

    @Column(name = "end_time")
    private Long endTime;

    @Column(name = "location_lat")
    private Double locationLat;

    @Column(name = "location_long")
    private Double locationLong;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @OneToOne
    @JoinColumn(unique = true)
    private DaySummary daySummary;

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

    public String getName() {
        return name;
    }

    public Day name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public Day displayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Day startTime(Long startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public Day endTime(Long endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Double getLocationLat() {
        return locationLat;
    }

    public Day locationLat(Double locationLat) {
        this.locationLat = locationLat;
        return this;
    }

    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }

    public Double getLocationLong() {
        return locationLong;
    }

    public Day locationLong(Double locationLong) {
        this.locationLong = locationLong;
        return this;
    }

    public void setLocationLong(Double locationLong) {
        this.locationLong = locationLong;
    }

    public String getCity() {
        return city;
    }

    public Day city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public Day country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public DaySummary getDaySummary() {
        return daySummary;
    }

    public Day daySummary(DaySummary daySummary) {
        this.daySummary = daySummary;
        return this;
    }

    public void setDaySummary(DaySummary daySummary) {
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
        Day day = (Day) o;
        if (day.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, day.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Day{" +
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
