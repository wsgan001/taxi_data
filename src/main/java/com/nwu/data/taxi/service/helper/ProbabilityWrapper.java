package com.nwu.data.taxi.service.helper;

import com.nwu.data.taxi.domain.model.GridProbability;
import com.nwu.data.taxi.domain.model.GridReading;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProbabilityWrapper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final int BY_DAY = 0;
    public static final int BY_WEEK = 1;
    public static final int BY_YEAR = 2;
    public static final int BY_HALF_HOUR = 3;
    public static final int HOUR_CHUNK = 2;
    public static final int DAY_CHUNK = 24;
    public static final int HALF_HOUR_CHUNK = 30;

    private HashMap<String, int[][]> entranceCounter;
    private HashMap<String, int[][]> pickedUpCounter;
    private int timeType;
    private int timeChunk;

    public ProbabilityWrapper(int timeType, int timeChunk) {
        this.timeType = timeType;
        this.timeChunk = timeChunk;
        entranceCounter = new HashMap<>();
        pickedUpCounter = new HashMap<>();
    }

    public void loadGridProbability(GridProbability gridProbability) {
        logger.info("loading data");
        if (gridProbability.getTimeType() == this.timeType){
            String time = gridProbability.getTime();
            prepareArray(time);
            int latBin = Config.decodeLatBin(gridProbability.getGrid());
            int lonBin = Config.decodeLonBin(gridProbability.getGrid());
            entranceCounter.get(time)[latBin][lonBin] = gridProbability.getEntrance();
            pickedUpCounter.get(time)[latBin][lonBin] = gridProbability.getPikedUp();
        }
        logger.info("loading finished");
    }

    private void prepareArray(String time) {
        if (entranceCounter.get(time) == null)
            entranceCounter
                    .put(time, new int[Config.NUM_OF_LAT_BINS][Config
                            .NUM_OF_LON_BINS]);
        if (pickedUpCounter.get(time) == null)
            pickedUpCounter
                    .put(time, new int[Config.NUM_OF_LAT_BINS][Config
                            .NUM_OF_LON_BINS]);
    }

    public void processByTaxi(GridReading gridReading) {
        String time = getTime(new Date(gridReading.getEventDateTime() * 1000));
        prepareArray(time);
        int grid = gridReading.getEventGrid();
        int latBin = Config.decodeLatBin(grid);
        int lonBin = Config.decodeLonBin(grid);
        if (latBin < 0 || latBin >= Config.NUM_OF_LAT_BINS)
            return;
        if (lonBin < 0 || lonBin >= Config.NUM_OF_LON_BINS)
            return;
        entranceCounter.get(time)[latBin][lonBin]++;
        if (gridReading.isPickedUp()) {
            pickedUpCounter.get(time)[latBin][lonBin]++;
        }
    }

    private String getTime(Date date) {
        if(timeType == BY_HALF_HOUR){
            return getTimeHalfHour(date);
        }

        else{
            return getTimeDayWeekYear(date);
        }
    }

    public String getTimeHalfHour(Date date){
        String formatedDate = Config.HALF_HOUR_FORMATTER.format(date);
        int minute = Integer.parseInt(formatedDate.substring(formatedDate.length()-2));
        String DateHour = formatedDate.substring(0, 10);
        String time;
        if(minute < 30)
        {
            time = DateHour + "00";
        }
        else
        {
           time = DateHour +"30";
        }
        return time;
    }

    public String getTimeDayWeekYear(Date date){
        String time = String.format("%02d", Integer.parseInt(Config.HOUR_FORMATTER.format(date)) / timeChunk * timeChunk);
        switch (timeType) {
            case BY_DAY:
                return time;
            case BY_WEEK:
                return Config.WEEK_FORMATTER.format(date) + time;
            case BY_YEAR:
                return Config.YEAR_FORMATTER.format(date) + time;
            default:
                return time;
        }
    }


    public List<GridProbability> generateProbabilities() {
        List<GridProbability> gridProbabilities = new ArrayList<>();
        for (String time : entranceCounter.keySet()) {
            int[][] entranceArray = entranceCounter.get(time);
            int[][] pickedUpArray = pickedUpCounter.get(time);
            for (int i = 0; i < Config.NUM_OF_LAT_BINS; i++) {
                for (int j = 0; j < Config.NUM_OF_LON_BINS; j++) {
                    int entrance = entranceArray[i][j];
                    int pickedUp = pickedUpArray[i][j];
                    int grid = Config.getGrid(i, j);
                    double probability = entrance == 0 ?  0 : (double) pickedUp / entrance;
                    gridProbabilities.add(new GridProbability(grid, probability, time, this.timeType, this.timeChunk, entrance, pickedUp));
                }
            }
        }
        return gridProbabilities;
    }

    public String getType(){
        switch (timeType) {
            case BY_DAY:
                return "day";
            case BY_WEEK:
                return "week day";
            case BY_YEAR:
                return "year date";
            case BY_HALF_HOUR:
                return "half hour";
            default:
                return "day";
        }
    }
}
