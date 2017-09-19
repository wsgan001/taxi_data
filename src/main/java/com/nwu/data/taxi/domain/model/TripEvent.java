package com.nwu.data.taxi.domain.model;

import javax.persistence.*;

@Entity
public class TripEvent {
    public static int TURN_ON = 1;
    public static int TURN_OFF = 0;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String eventDate;
    private String eventTime;
    private int eventType;
    private int eventGrid;
    private long eventDateTime;
    private long duration;
    @ManyToOne
    @JoinColumn (name = "taxi_id")
    private Taxi taxi;
    private String taxiName;

    public TripEvent() {
    }

    public TripEvent(String eventDate, String eventTime, int eventType, int eventGrid, long eventDateTime, long duration, Taxi taxi) {
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventType = eventType;
        this.eventGrid = eventGrid;
        this.eventDateTime = eventDateTime;
        this.duration = duration;
        this.taxi = taxi;
        this.taxiName = taxi.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public int getEventGrid() {
        return eventGrid;
    }

    public void setEventGrid(int eventGrid) {
        this.eventGrid = eventGrid;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
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

    public String getTaxiName() {
        return taxiName;
    }

    public void setTaxiName(String taxiName) {
        this.taxiName = taxiName;
    }
}

