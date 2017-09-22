package com.nwu.data.taxi.service.helper.model;


import com.nwu.data.taxi.domain.model.Taxi;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private String name;
    private List<Grid> cluster;
    private List<Grid> route;
    private Grid currentGrid;
    private boolean isOccupied;
    private double travelDistance;
    private double liveDistance;
    private double travelTime;
    private double liveTime;
    private int numOfHunts;
    private boolean isOn;
    private Taxi taxi;

    public boolean isOn() {
        return isOn;
    }
    public void setOn() {
        this.isOn = true;
    }
    public void setOff() {
        this.isOn = false;
    }
    public Vehicle(Taxi taxi) {
        this.name = taxi.getName();
        this.currentGrid = null;
        this.taxi = taxi;
        this.cluster = new ArrayList<>();
        this.route = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name + " " + currentGrid;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public List<Grid> getCluster() {
        return cluster;
    }

    public void setCluster(List<Grid> cluster) {
        this.cluster = cluster;
    }

    public List<Grid> getRoute() {
        return route;
    }

    public void setRoute(List<Grid> route) {
        this.route = route;
    }

    public double getRouteTime() {
        if (this.getRoute() == null || this.getRoute().isEmpty())
            return -1;
        else {
            Grid g0 = currentGrid;
            double sum = 0;
            for (Grid g : this.getRoute()) {
                sum += g0.getTime().get(g);
                g0 = g;
            }
            return sum;
        }
    }

    public void setTravelDistance(double travelDistance) {
        this.travelDistance = travelDistance;
    }

    public void setLiveDistance(double liveDistance) {
        this.liveDistance = liveDistance;
    }

    public double getTravelDistance() {
        return travelDistance;
    }

    public double getLiveDistance() {
        return liveDistance;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public void hunt() {
        numOfHunts++;
        isOccupied = true;
    }
    public int getNumOfHunts() {
        return numOfHunts;
    }

    public Grid getRoute(int i) {
        return this.getRoute().get(i);
    }

    public double getDistancePerformance() {
        return this.getLiveDistance() == 0 ? 0 : this.getLiveDistance()/this.getTravelDistance();
    }
    public double getLiveTime() {
        return liveTime;
    }
    public double getTravelTime() {
        return travelTime;
    }
    public void setLiveTime(double liveTime) {
        this.liveTime = liveTime;
    }
    public void setTravelTime(double travelTime) {
        this.travelTime = travelTime;
    }
    public double getTimePerformance() {
        return this.getLiveTime() == 0 ? 0 : this.getLiveTime()/this.getTravelTime();
    }

    public Grid getCurrentGrid() {
        return currentGrid;
    }

    public void setCurrentGrid(Grid currentGrid) {
        this.currentGrid = currentGrid;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }
}
