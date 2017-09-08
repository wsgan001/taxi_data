package com.nwu.data.taxi.service.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.nwu.data.taxi.domain.model.GPSData;
import com.nwu.data.taxi.domain.model.GPSReading;

public class FileRawInputProcessor {

    public Iterable<GPSReading> process(File f, byte fromStatus, byte toStatus) {
        Scanner sc;
        List pickUpLocations = new ArrayList<GPSReading>();
        try {
            sc = new Scanner(f);
            String name = f.getName().split("\\.")[0];
            GPSReading lastReading = new GPSReading(name,0, 0, 0, (byte) 0);
            while (sc.hasNext()) {
                String line = sc.nextLine();
                GPSReading thisReading = parseLine( name, line );
                // the condition is reversed because the file is in the reverse order of time!! :)
                if (lastReading.getStatus() == toStatus && thisReading.getStatus() == fromStatus ) {
                    pickUpLocations.add( lastReading );
                }
                lastReading = thisReading;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            return pickUpLocations;
        }
    }

    private GPSReading parseLine(String name, String nextLine) {
        Scanner sc = new Scanner(nextLine);
        double lat = sc.nextDouble();
        double lon = sc.nextDouble();
        byte status = (byte) (sc.next().charAt(0) == '1' ? 1 : 0);
        long time = sc.nextLong();
        return new GPSReading(name, lat, lon, time, status);
    }

    private GPSData parseGPSData(String name, String nextLine) {
        Scanner sc = new Scanner(nextLine);
        double lat = sc.nextDouble();
        double lon = sc.nextDouble();
        byte status = (byte) (sc.next().charAt(0) == '1' ? 1 : 0);
        long time = sc.nextLong();
        return new GPSData(name, lat, lon, time, status);
    }

    public Iterable<GPSData> process(File f) {
        Scanner sc;
        List pickUpLocations = new ArrayList<GPSData>();
        try {
            sc = new Scanner(f);
            String name = f.getName().split("\\.")[0];
            while (sc.hasNext()) {
                String line = sc.nextLine();
                GPSData thisData = parseGPSData( name, line );
                pickUpLocations.add( thisData );
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            return pickUpLocations;
        }
    }
}
