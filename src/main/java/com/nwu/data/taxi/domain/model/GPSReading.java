package com.nwu.data.taxi.domain.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nwu.data.taxi.service.helper.Config;

import javax.persistence.*;
import java.util.Date;

@Entity
public class GPSReading {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;
    private double lat;
    private double lon;
    private long time;
    private byte status;
    @ManyToOne
    @JoinColumn (name = "taxi_id")
    @JsonBackReference
    private Taxi taxi;

    public GPSReading() {
    }

    public GPSReading(String name, double lat, double lon, long time, byte status, Taxi taxi) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
        this.status = status;
        this.taxi = taxi;
    }

    public GPSReading(String name, int i, int i1, int i2, byte b) {
    }

    public GPSReading(String name, double lat, double lon, long time, byte status) {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public boolean isOccupied() {
        return status == 1;
    }

    public Integer getGrid() {
        return Config.getLatBin(this.getLat()) * Config.NUM_OF_LON_BINS + Config.getLonBin(this.getLon());
    }

    public Date getDate() {
        return new Date(this.getTime() * 1000);
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }
}
