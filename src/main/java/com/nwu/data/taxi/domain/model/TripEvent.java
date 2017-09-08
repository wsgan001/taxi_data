package com.nwu.data.taxi.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TripEvent {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private long eventDate;
    private byte tripType;
    private long eventTime;
    private Integer eventGrid;
    private long duration;

    public TripEvent() {
    }

    public TripEvent(long eventDate, byte tripType, long eventTime, Integer eventGrid, long duration) {
        this.eventDate = eventDate;
        this.tripType = tripType;
        this.eventTime = eventTime;
        this.eventGrid = eventGrid;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getEventDate() {
        return eventDate;
    }

    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
    }

    public byte getTripType() {
        return tripType;
    }

    public void setTripType(byte tripType) {
        this.tripType = tripType;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public Integer getEventGrid() {
        return eventGrid;
    }

    public void setEventGrid(Integer eventGrid) {
        this.eventGrid = eventGrid;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
