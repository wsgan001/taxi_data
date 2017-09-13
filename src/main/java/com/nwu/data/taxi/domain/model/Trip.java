package com.nwu.data.taxi.domain.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Trip {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Integer eventGrid;
    private long eventDate;
    private long eventTime;

    public Trip() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventGrid() {
        return eventGrid;
    }

    public void setEventGrid(Integer eventGrid) {
        this.eventGrid = eventGrid;
    }

    public long getEventDate() {
        return eventDate;
    }

    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }
}
