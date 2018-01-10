package com.nwu.data.taxi.web;

import com.nwu.data.taxi.domain.model.Performance;
import com.nwu.data.taxi.service.PerformanceService;
import com.nwu.data.taxi.service.SimulatorService;
import com.nwu.data.taxi.service.helper.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Date;

@Controller
@RequestMapping("/simulator")
public class SimulatorController {
    @Autowired
    private SimulatorService simulatorService;
    @Autowired
    private PerformanceService performanceService;

    @RequestMapping(method = RequestMethod.GET, path = "/recommend")
    public @ResponseBody Iterable<Performance> recommendByWeek (@RequestParam(value = "num", defaultValue = "50") int num,
                                                                @RequestParam(value = "date", defaultValue = "0517") String date,
                                                                @RequestParam(value = "type", defaultValue = "2") int type) {
        String[] DATES = {"0609"};
        int[] recommenderTypes = {Config.MY, Config.NEIGHBOR, Config.AVG};
        for (int i = 0;i< DATES.length; i++) {
            for (int recommenderType : recommenderTypes){
                Date d = null;
                try {
                    d = Config.DATE_FORMATTER.parse("2008" + DATES[i]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long time = d.getTime() / 1000;
                simulatorService.initEnvironment(time, time + 24 * 3600, num);
                simulatorService.start(recommenderType, time);
            }
        }

        return performanceService.getResult("2008" + date, type);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/real")
    public @ResponseBody Iterable<Performance> analyzeRealData (@RequestParam(value = "num", defaultValue = "20") int num,
                                                                @RequestParam(value = "date", defaultValue = "0517") String date,
                                                                @RequestParam(value = "type", defaultValue = "2") int type,
                                                                @RequestParam(value = "day", defaultValue = "1") int day) {
        Date d = null;
        try {
            d = Config.DATE_FORMATTER.parse("2008" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = d.getTime() / 1000;
        for (int i = 0; i < day; i++) {
            simulatorService.initRealWord(time, time + 24 * 3600, num);
            simulatorService.analyzeReal();
            time += 24 * 3600;
        }

        return performanceService.getResult("2008" + date, type);
    }

}
