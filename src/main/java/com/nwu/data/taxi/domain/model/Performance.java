package com.nwu.data.taxi.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nwu.data.taxi.service.helper.Config;
import com.nwu.data.taxi.service.helper.model.Vehicle;

import javax.persistence.*;
import java.util.Date;

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
    private String time;
    private String date;
    private int type;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "taxi_id")
    private Taxi taxi;

    public Performance() {
    }

    public Performance(Vehicle v, Date currentTime, int type) {
        this.travelDistance = v.getTravelDistance();
        this.liveDistance = v.getLiveDistance();
        this.distancePerformance = v.getDistancePerformance();
        this.travelTime = v.getTravelTime();
        this.liveTime = v.getLiveTime();
        this.timePerformance = v.getTimePerformance();
        this.passengerNum = v.getNumOfHunts();
        this.taxiName = v.getName();
        this.taxi = v.getTaxi();
        this.time = Config.TIME_FORMATTER.format(currentTime);
        this.date = Config.DATE_FORMATTER.format(currentTime);
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
