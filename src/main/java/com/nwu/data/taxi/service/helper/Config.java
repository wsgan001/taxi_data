package com.nwu.data.taxi.service.helper;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Config {
    private static File dataFolder;
    public static FilenameFilter FILE_FILTER = (dir1, name) -> name.endsWith(".txt");
    public static final double ANGLE_CHUNK = 0.005;
    public static final double MIN_LAT = 37.6;
    public static final double MAX_LAT = 37.824;
    public static final double MIN_LON = -122.526;
    public static final double MAX_LON = -122.35;
    public static final int NUM_OF_LAT_BINS = (int) ((MAX_LAT - MIN_LAT) / ANGLE_CHUNK) + 1;
    public static final int NUM_OF_LON_BINS = (int) ((MAX_LON - MIN_LON) / ANGLE_CHUNK) + 1;
    public static final long MAX_TIME_INTERVAL = 420;

    public static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("Hmmss");

    public static File getDataFolder(){
        try {
            if(null == dataFolder) {
                dataFolder = new ClassPathResource("cabspottingdata").getFile();
            }
        } catch (IOException e) {
            System.out.print("failed to load GPSReading data");
        } finally {
            return dataFolder;
        }

    }

    public static int getLatBin(double lat){
        return (int) ((lat- MIN_LAT)/ ANGLE_CHUNK);
    }

    public static int getLonBin(double lon){
        return (int) ((lon- MIN_LON)/ ANGLE_CHUNK);
    }

}
