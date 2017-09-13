package com.nwu.data.taxi.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String travelDate;
    private Integer startGrid;
    private Integer endGrid;
    private long travelTime;
    @OneToOne
    @JoinColumn(name = "start_gps_id")
    private GPSData startGPSReading;
    @OneToOne
    @JoinColumn(name = "end_gps_id")
    private GPSData endGPSReading;
    @ManyToOne
    @JoinColumn(name = "taxi_id")
    private Taxi taxi;

    public Passenger() {
    }

    public Passenger(GPSData start, GPSData end) {
        this.travelDate = start.getDateString();
        this.startGrid = start.getGrid();
        this.endGrid = null == end ? null : end.getGrid();
        this.travelTime = start.getTime();
        this.taxi = start.getTaxi();
        this.startGPSReading = start;
        this.endGPSReading = end;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public Integer getStartGrid() {
        return startGrid;
    }

    public void setStartGrid(Integer startGrid) {
        this.startGrid = startGrid;
    }

    public Integer getEndGrid() {
        return endGrid;
    }

    public void setEndGrid(Integer endGrid) {
        this.endGrid = endGrid;
    }

    public long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(long travelTime) {
        this.travelTime = travelTime;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }

    public GPSData getStartGPSReading() {
        return startGPSReading;
    }

    public void setStartGPSReading(GPSData startGPSReading) {
        this.startGPSReading = startGPSReading;
    }

    public GPSData getEndGPSReading() {
        return endGPSReading;
    }

    public void setEndGPSReading(GPSData endGPSReading) {
        this.endGPSReading = endGPSReading;
    }
}
