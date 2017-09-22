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
    public @ResponseBody Iterable<Performance> getTaxi (@RequestParam(value = "num", defaultValue = "20") int num,
                                                        @RequestParam(value = "date", defaultValue = "0517") String date,
                                                        @RequestParam(value = "type", defaultValue = "2") int type) {
        Date d = null;
        try {
            d = Config.DATE_FORMATTER.parse("2008" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = d.getTime() / 1000;
        simulatorService.initEnvironment(type, time, time + 24 * 3600, num);
        simulatorService.start();
        return performanceService.getResult("2008" + date, type);
    }

}
