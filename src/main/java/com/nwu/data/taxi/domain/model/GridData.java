package com.nwu.data.taxi.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GridData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private int grid;
    private long lat;
    private long lon;
    private boolean isSelected;
    public GridData() {
    }

    public GridData(int grid, long lat, long lon, boolean isSelected) {
        this.grid = grid;
        this.lat = lat;
        this.lon = lon;
        this.isSelected = isSelected;
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

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
