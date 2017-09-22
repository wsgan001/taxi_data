package com.nwu.data.taxi.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Taxi {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "taxi", fetch = FetchType.LAZY)
    @OrderBy("time ASC")
    @JsonBackReference
    private Set<GPSData> gpsData;

    @OneToMany(mappedBy = "taxi", fetch = FetchType.LAZY)
    @OrderBy("time ASC")
    @JsonBackReference
    private Set<GPSReading> gpsReading;

    @OneToMany(mappedBy = "taxi", fetch = FetchType.LAZY)
    @OrderBy("event_time ASC")
    @JsonBackReference
    private Set<TripEvent> tripEvents;

    @OneToMany(mappedBy = "taxi", fetch = FetchType.LAZY)
    @OrderBy("travel_time ASC")
    @JsonBackReference
    private Set<PassengerData> passengerData;

    @OneToMany(mappedBy = "taxi", fetch = FetchType.LAZY)
    @OrderBy("event_date_time ASC")
    @JsonBackReference
    private Set<GridReading> gridReading;

    @OneToMany(mappedBy = "taxi", fetch = FetchType.LAZY)
    @JsonBackReference
    @OrderBy("date ASC, time ASC")
    private Set<Performance> performance;

    private String location;

    public Taxi() {
    }

    public Taxi(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GPSData> getGpsData() {
        return gpsData;
    }

    public void setGpsData(Set<GPSData> gpsData) {
        this.gpsData = gpsData;
    }

    public Set<GPSReading> getGpsReading() {
        return gpsReading;
    }

    public void setGpsReading(Set<GPSReading> gpsReading) {
        this.gpsReading = gpsReading;
    }

    public Set<TripEvent> getTripEvents() {
        return tripEvents;
    }

    public void setTripEvents(Set<TripEvent> tripEvents) {
        this.tripEvents = tripEvents;
    }

    public Set<PassengerData> getPassengerData() {
        return passengerData;
    }

    public void setPassengerData(Set<PassengerData> passengerData) {
        this.passengerData = passengerData;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<GridReading> getGridReading() {
        return gridReading;
    }

    public void setGridReading(Set<GridReading> gridReading) {
        this.gridReading = gridReading;
    }

    public Set<Performance> getPerformance() {
        return performance;
    }

    public void setPerformance(Set<Performance> performance) {
        this.performance = performance;
    }
}
