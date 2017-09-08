package com.nwu.data.taxi.service.helper;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class Config {
    private static File dataFolder;
    private static FilenameFilter fileFilter;
    private static int maxnumberoffilestoread = 1000;
    private static final double angleChunk = 0.005;
    private static final double minLat = 37.6;
    private static final double maxLat = 37.824;
    private static final double minLon = -122.526;
    private static final double maxLon = -122.35;
    private static final int numOfLatBins = (int) ((maxLat - minLat) / angleChunk) + 1;
    private static final int numOfLonBins = (int) ((maxLon - minLon) / angleChunk) + 1;
    private static final long MaxTimeInterval = 420;


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

    public static FilenameFilter getFileFilter(){
        if(null == fileFilter) {
            fileFilter = (dir1, name) -> name.endsWith(".txt");
        }
        return fileFilter;
    }

    public static int getMaxnumberoffilestoread() {
        return maxnumberoffilestoread;
    }


    public static int getNumoflatbins() {
        return numOfLatBins;
    }

    public static int getNumoflonbins() {
        return numOfLonBins;
    }

    public static int getLatBin(double lat){
        return (int) ((lat-minLat)/angleChunk);
    }

    public static int getLonBin(double lon){
        return (int) ((lon-minLon)/angleChunk);
    }

    public static long getMaxTimeInterval() {
        return MaxTimeInterval;
    }
}
