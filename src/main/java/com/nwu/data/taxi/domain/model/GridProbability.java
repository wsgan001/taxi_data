package com.nwu.data.taxi.domain.model;

import javax.persistence.*;
@Entity
public class GridProbability {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private int grid;
    private double probability;
    private String time;
    private int timeType;
    private int timeChunk;
    private int entrance;
    private int pikedUp;

    public GridProbability() {
    }

    public GridProbability(int eventGrid, double probability, String time, int timeType, int timeChunk, int entrance, int pikedUp) {
        this.grid = eventGrid;
        this.probability = probability;
        this.time = time;
        this.timeType = timeType;
        this.timeChunk = timeChunk;
        this.entrance = entrance;
        this.pikedUp = pikedUp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getGrid() {
        return grid;
    }

    public void setGrid(int grid) {
        this.grid = grid;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public int getEntrance() {
        return entrance;
    }

    public void setEntrance(int entrance) {
        this.entrance = entrance;
    }

    public int getPikedUp() {
        return pikedUp;
    }

    public void setPikedUp(int pikedUp) {
        this.pikedUp = pikedUp;
    }

    public int getTimeChunk() {
        return timeChunk;
    }

    public void setTimeChunk(int timeChunk) {
        this.timeChunk = timeChunk;
    }
}
