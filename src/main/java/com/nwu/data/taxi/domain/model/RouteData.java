package com.nwu.data.taxi.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RouteData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Integer fromGrid;
    private Integer toGrid;
    private long duration;
    private Integer count;


    public RouteData() {
    }

    public RouteData(Integer fromGrid, Integer toGrid, long duration, Integer count) {
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

    public Integer getFromGrid() {
        return fromGrid;
    }

    public void setFromGrid(Integer fromGrid) {
        this.fromGrid = fromGrid;
    }

    public Integer getToGrid() {
        return toGrid;
    }

    public void setToGrid(Integer toGrid) {
        this.toGrid = toGrid;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
