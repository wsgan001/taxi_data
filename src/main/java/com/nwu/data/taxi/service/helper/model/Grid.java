package com.nwu.data.taxi.service.helper.model;


import com.nwu.data.taxi.domain.model.GPSData;
import com.nwu.data.taxi.domain.model.PassengerData;
import com.nwu.data.taxi.service.helper.Config;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class Grid {
    private int latBin, lonBin;
    /*
     * Stores the neighboring grids of this grid
     */
    private List<Grid> neighbors;
    /*
     * Stores the average time to go from this grid to a neighboring grid
     */
    private HashMap<Grid, Double> time;
    /*
     * Stores the distance between the center point of this grid and the center
     * point of a neighboring grid.
     */
    private HashMap<Grid, Double> distance;

    private double probability;

    private int maxNumberOfTaxis;

    private List<Passenger> passengers;

    private Grid() {
        neighbors = new ArrayList<>();
        time = new HashMap<>();
        distance = new HashMap<>();
        passengers = new ArrayList<>();
    }

    public Grid(int latBin, int lonBin) {
        this();
        this.latBin = latBin;
        this.lonBin = lonBin;
    }

    public Grid(int id) {
        this();
        latBin = unHashLatBin(id);
        lonBin = unHashLonBin(id);
    }

    private int unHashLonBin(int id) {
        return id % Config.NUM_OF_LON_BINS;
    }

    private int unHashLatBin(int id) {
        return id / Config.NUM_OF_LON_BINS;
    }

    /*
     * adds a grid to the neighbors of this grid.
     */
    public void addNeighbor(Grid g, double time) {
        if (this.hashCode() != g.hashCode()) {
            this.neighbors.add(g);
            this.time.put(g, time);
            double distance = Config.getDistance(this.getCenterPoint(),
                    g.getCenterPoint());
            this.distance.put(g, distance);
        }
    }

    /*
     * returns the center point of the grid
     */
    public GPSData getCenterPoint() {
        double lat = (this.latBin + .5) * Config.ANGLE_CHUNK
                + Config.MAX_LAT;
        double lon = (this.lonBin + .5) * Config.ANGLE_CHUNK
                + Config.MAX_LAT;
        return new GPSData(lat, lon);
    }

    @Override
    public int hashCode() {
        return latBin * Config.NUM_OF_LON_BINS + lonBin;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    private String getName() {
        return "(" + latBin + "," + lonBin + ")";
    }

    public boolean isHighProbability() {
      return this.probability > Config.PROBABILITY_THRESHOLD;
    }

    public int getLatBin() {
        return latBin;
    }

    public void setLatBin(int latBin) {
        this.latBin = latBin;
    }

    public int getLonBin() {
        return lonBin;
    }

    public void setLonBin(int lonBin) {
        this.lonBin = lonBin;
    }

    public List<Grid> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Grid> neighbors) {
        this.neighbors = neighbors;
    }

    public HashMap<Grid, Double> getTime() {
        return time;
    }

    public void setTime(HashMap<Grid, Double> time) {
        this.time = time;
    }

    public HashMap<Grid, Double> getDistance() {
        return distance;
    }

    public void setDistance(HashMap<Grid, Double> distance) {
        this.distance = distance;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public int getMaxNumberOfTaxis() {
        return maxNumberOfTaxis;
    }

    public void setMaxNumberOfTaxis(int maxNumberOfTaxis) {
        this.maxNumberOfTaxis = maxNumberOfTaxis;
    }

    public double getDistance(Grid g) {
        return Config.getDistance(this.getCenterPoint(), g.getCenterPoint());
    }

    public double getObjective(Grid g) {
        return this.getDistance(g);
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public double getTime(Grid g) {
        return this.getTime().get(g);
    }

}
