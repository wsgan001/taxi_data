package com.nwu.data.taxi.domain.model;

import javax.persistence.*;

@Entity
public class PassengerData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String travelDate;
    private String travelTime;
    private Integer startGrid;
    private Integer endGrid;
    private long travelDateTime;
    @OneToOne
    @JoinColumn(name = "start_gps_id")
    private GPSData startGPSReading;
    @OneToOne
    @JoinColumn(name = "end_gps_id")
    private GPSData endGPSReading;
    @ManyToOne
    @JoinColumn(name = "taxi_id")
    private Taxi taxi;

    public PassengerData() {
    }

    public PassengerData(GPSData start, GPSData end) {
        this.travelDate = start.getDateString();
        this.travelTime = start.getTimeString();
        this.startGrid = start.getGrid();
        this.endGrid = end.getGrid();
        this.travelDateTime = start.getTime();
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

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
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

    public long getTravelDateTime() {
        return travelDateTime;
    }

    public void setTravelDateTime(long travelDateTime) {
        this.travelDateTime = travelDateTime;
    }
}
