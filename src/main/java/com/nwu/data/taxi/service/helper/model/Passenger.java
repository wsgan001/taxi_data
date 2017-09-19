package com.nwu.data.taxi.service.helper.model;

public class Passenger {
    private Grid location;
    private Grid destination;
    private int waitingTime;

    public Passenger(Grid location, Grid destination, int waitingTime) {
        this.location = location;
        this.destination = destination;
        this.waitingTime = waitingTime;
    }

    public Grid getLocation() {
        return location;
    }

    public void setLocation(Grid location) {
        this.location = location;
    }

    public Grid getDestination() {
        return destination;
    }

    public void setDestination(Grid destination) {
        this.destination = destination;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
}
