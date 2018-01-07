package com.nwu.data.taxi.domain.model;

public interface GridProbabilityI {
    int getGrid();
    double getProbability();
    String getTime();
    int getTimeType();
    int getEntrance();
    int getPikedUp();
    int getTimeChunk();
}
