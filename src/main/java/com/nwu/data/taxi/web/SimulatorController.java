package com.nwu.data.taxi.web;

import com.nwu.data.taxi.domain.model.*;
import com.nwu.data.taxi.service.GPSReadingService;
import com.nwu.data.taxi.service.SimulatorService;
import com.nwu.data.taxi.service.helper.Config;
import com.nwu.data.taxi.service.helper.Recommender;
import com.nwu.data.taxi.service.helper.recommender.MyRecommender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

@Controller
@RequestMapping("/simulator")
public class SimulatorController {
    @Autowired
    private SimulatorService simulatorService;

    @RequestMapping(method = RequestMethod.GET, path = "/recommend")
    public @ResponseBody Iterable<Performance> getTaxi (@RequestParam(value = "num", defaultValue = "50") int num, @RequestParam(value = "date", defaultValue = "0517") String date) {
        Recommender recommender = new MyRecommender();
        Date d = null;
        try {
            d = Config.DATE_FORMATTER.parse("2008" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = d.getTime() / 1000;
        return simulatorService.process(recommender, time, time + 24 * 3600, num);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/performance")
    public @ResponseBody Iterable<Performance> getPerformance (@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "50") int pageSize) {
        return simulatorService.getPerformance(pageNum, pageSize);
    }
}
