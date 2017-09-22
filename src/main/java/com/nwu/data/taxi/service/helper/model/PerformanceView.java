package com.nwu.data.taxi.service.helper.model;

import com.nwu.data.taxi.domain.model.Performance;

public class PerformanceView {
    private String time;
    private double distancePerformance;
    private double timePerformance;
    private double travelDistance;
    private double travelTime;
    private double liveDistance;
    private double liveTime;

    public PerformanceView(Performance performance) {
        this.time = performance.getTime();
        this.travelDistance = performance.getTravelDistance();
        this.liveDistance = performance.getLiveDistance();
        this.travelTime = performance.getTravelTime();
        this.liveTime = performance.getLiveTime();
    }

    public void addPerformance(Performance performance) {
        this.travelDistance += performance.getTravelDistance();
        this.liveDistance += performance.getLiveDistance();
        this.travelTime += performance.getTravelTime();
        this.liveTime += performance.getLiveTime();
    }

    public void calculate() {
        this.distancePerformance = liveDistance == 0 ? 0 : liveDistance / travelDistance;
        this.timePerformance = liveTime == 0 ? 0 : liveTime / travelTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getDistancePerformance() {
        return distancePerformance;
    }

    public void setDistancePerformance(double distancePerformance) {
        this.distancePerformance = distancePerformance;
    }

    public double getTimePerformance() {
        return timePerformance;
    }

    public void setTimePerformance(double timePerformance) {
        this.timePerformance = timePerformance;
    }

    public double getTravelDistance() {
        return travelDistance;
    }

    public void setTravelDistance(double travelDistance) {
        this.travelDistance = travelDistance;
    }

    public double getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(double travelTime) {
        this.travelTime = travelTime;
    }

    public double getLiveDistance() {
        return liveDistance;
    }

    public void setLiveDistance(double liveDistance) {
        this.liveDistance = liveDistance;
    }

    public double getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(double liveTime) {
        this.liveTime = liveTime;
    }


}
