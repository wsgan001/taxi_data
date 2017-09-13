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
    @OrderBy("time DESC")
    @JsonBackReference
    private Set<GPSData> gpsData;

    @OneToMany(mappedBy = "taxi", fetch = FetchType.LAZY)
    @OrderBy("time DESC")
    @JsonBackReference
    private Set<GPSReading> gpsReading;

    @OneToMany(mappedBy = "taxi", fetch = FetchType.LAZY)
    @OrderBy("event_time ASC")
    @JsonBackReference
    private Set<TripEvent> tripEvents;

    @OneToMany(mappedBy = "taxi", fetch = FetchType.LAZY)
    @OrderBy("travel_time ASC")
    @JsonBackReference
    private Set<Passenger> passengers;

    public Taxi() {
    }

    public Taxi(Integer id, String name, Set<GPSData> gpsData, Set<GPSReading> gpsReading, Set<TripEvent> tripEvents, Set<Passenger> passengers) {
        this.id = id;
        this.name = name;
        this.gpsData = gpsData;
        this.gpsReading = gpsReading;
        this.tripEvents = tripEvents;
        this.passengers = passengers;
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

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }
}
