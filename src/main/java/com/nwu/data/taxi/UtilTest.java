package com.nwu.data.taxi;

import com.nwu.data.taxi.service.helper.Config;
import com.nwu.data.taxi.service.helper.model.Grid;

import java.util.*;

public class UtilTest {
    public static void main(String[] args) {
//        Date date = new Date(121124800000L);
//        System.out.println(date);
//
//        String formatedDate = Config.HALF_HOUR_FORMATTER.format(date);
//        int minute = Integer.parseInt(formatedDate.substring(formatedDate.length()-2));
//        String DateHour = formatedDate.substring(0, 10);
//        //String time;
//        if(minute < 30)
//        {
//            System.out.println(DateHour + "00");
//            //return time;
//        }
//        else
//        {
//            System.out.println(DateHour +"30");
//        }
        List<Grid> grids = new ArrayList<>();
        grids.add(new Grid(1,10));
        grids.add(new Grid(1,20));
        grids.add(new Grid(1,30));
        grids.add(new Grid(2,40));
        grids.add(new Grid(2,60));
        grids.add(new Grid(3,50));

        grids.sort(Comparator.comparingDouble(Grid::getLatBin));

        for (Grid grid : grids) {
            System.out.println("lat: " + grid.getLatBin() + "lon: " + grid.getLonBin());
        }
    }
}