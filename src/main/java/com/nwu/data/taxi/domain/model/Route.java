package com.nwu.data.taxi.domain.model;

import javax.persistence.*;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private int fromGrid;
    private int toGrid;
    private long duration;
    private int count;


    public Route() {
    }

    public Route(int fromGrid , int toGrid, long duration, int count) {
        this.fromGrid = fromGrid;
        this.toGrid = toGrid;
        this.duration = duration;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFromGrid() {
        return fromGrid;
    }

    public void setFromGrid(int fromGrid) {
        this.fromGrid = fromGrid;
    }

    public int getToGrid() {
        return toGrid;
    }

    public void setToGrid(int toGrid) {
        this.toGrid = toGrid;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTime() {
        return duration/count;
    }
}
