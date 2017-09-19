package com.nwu.data.taxi.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nwu.data.taxi.service.helper.model.Vehicle;

import javax.persistence.*;

@Entity
public class Performance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private double travelDistance;
    private double liveDistance;
    private double distancePerformance;
    private double travelTime;
    private double liveTime;
    private double timePerformance;
    private int passengerNum;
    private String taxiName;
    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "taxi_id")
    private Taxi taxi;

    public Performance() {
    }

    public Performance(Vehicle v, Taxi taxi) {
        this.travelDistance = v.getTravelDistance();
        this.liveDistance = v.getLiveDistance();
        this.distancePerformance = v.getDistancePerformance();
        this.travelTime = v.getTravelTime();
        this.liveTime = v.getLiveTime();
        this.timePerformance = v.getTimePerformance();
        this.passengerNum = v.getNumOfHunts();
        this.taxiName = v.getName();
        this.taxi = taxi;
    }

    public double getTravelDistance() {
        return travelDistance;
    }

    public void setTravelDistance(double travelDistance) {
        this.travelDistance = travelDistance;
    }

    public double getLiveDistance() {
        return liveDistance;
    }

    public void setLiveDistance(double liveDistance) {
        this.liveDistance = liveDistance;
    }

    public double getDistancePerformance() {
        return distancePerformance;
    }

    public void setDistancePerformance(double distancePerformance) {
        this.distancePerformance = distancePerformance;
    }

    public double getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(double travelTime) {
        this.travelTime = travelTime;
    }

    public double getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(double liveTime) {
        this.liveTime = liveTime;
    }

    public double getTimePerformance() {
        return timePerformance;
    }

    public void setTimePerformance(double timePerformance) {
        this.timePerformance = timePerformance;
    }

    public int getPassengerNum() {
        return passengerNum;
    }

    public void setPassengerNum(int passengerNum) {
        this.passengerNum = passengerNum;
    }

    public String getTaxiName() {
        return taxiName;
    }

    public void setTaxiName(String taxiName) {
        this.taxiName = taxiName;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }
}
