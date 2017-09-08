package com.nwu.data.taxi.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private long travelDate;
    private Integer startLocation;
    private Integer endLocation;
    private long travelTime;

    public Passenger() {
    }

    public Passenger(long travelDate, Integer startLocation, Integer endLocation, long travelTime) {
        this.travelDate = travelDate;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.travelTime = travelTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(long travelDate) {
        this.travelDate = travelDate;
    }

    public Integer getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Integer startLocation) {
        this.startLocation = startLocation;
    }

    public Integer getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Integer endLocation) {
        this.endLocation = endLocation;
    }

    public long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(long travelTime) {
        this.travelTime = travelTime;
    }
}
