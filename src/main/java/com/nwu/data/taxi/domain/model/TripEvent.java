package com.nwu.data.taxi.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class TripEvent {
    public static Integer TURN_ON = 1;
    public static Integer TURN_OFF = 0;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private long eventDate;
    private long eventTime;
    private Integer eventType;
    private Integer eventGrid;
    private long eventDateTime;
    private long duration;
    @ManyToOne
    @JoinColumn (name = "taxi_id")
    private Taxi taxi;

    public TripEvent() {
    }

    public TripEvent(long eventDate, long eventTime, Integer eventType, Integer eventGrid, long eventDateTime, long duration, Taxi taxi) {
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventType = eventType;
        this.eventGrid = eventGrid;
        this.eventDateTime = eventDateTime;
        this.duration = duration;
        this.taxi = taxi;
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

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(long eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }
}

