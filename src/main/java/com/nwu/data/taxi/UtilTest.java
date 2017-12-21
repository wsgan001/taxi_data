package com.nwu.data.taxi;

import com.nwu.data.taxi.service.helper.Config;

import java.util.*;

public class UtilTest {
    public static void main(String[] args) {
        Date date = new Date(121124800000L);
        System.out.println(date);

        String formatedDate = Config.HALF_HOUR_FORMATTER.format(date);
        int minute = Integer.parseInt(formatedDate.substring(formatedDate.length()-2));
        String DateHour = formatedDate.substring(0, 10);
        //String time;
        if(minute < 30)
        {
            System.out.println(DateHour + "00");
            //return time;
        }
        else
        {
            System.out.println(DateHour +"30");
        }
    }
}