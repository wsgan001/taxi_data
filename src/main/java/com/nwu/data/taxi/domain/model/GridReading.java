package com.nwu.data.taxi.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class GridReading {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private int eventGrid;
    private String eventDate;
    private String eventTime;
    private long eventDateTime;
    private boolean isPickedUp;
    private byte status;
    @ManyToOne
    @JoinColumn (name = "taxi_id")
    @JsonBackReference
    private Taxi taxi;

    public GridReading() {
    }

    public GridReading(GPSData gpsData) {
        this.eventGrid = gpsData.getGrid();
        this.eventDate = gpsData.getDateString();
        this.eventTime = gpsData.getTimeString();
        this.eventDateTime = gpsData.getTime();
        this.status = gpsData.getStatus();
        this.taxi = gpsData.getTaxi();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEventGrid() {
        return eventGrid;
    }

    public void setEventGrid(int eventGrid) {
        this.eventGrid = eventGrid;
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

    public long getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(long eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public void setIsPickedUp(boolean isPickedUp) {
        this.isPickedUp = isPickedUp;
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }

    public void setPickedUp(boolean isPickedUp) {
        this.isPickedUp = isPickedUp;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }
}
