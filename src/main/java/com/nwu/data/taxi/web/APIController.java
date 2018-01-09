package com.nwu.data.taxi.web;

import com.nwu.data.taxi.domain.model.GridProbability;
import com.nwu.data.taxi.service.GPSReadingService;
import com.nwu.data.taxi.service.KalService;
import com.nwu.data.taxi.service.PerformanceService;
import com.nwu.data.taxi.service.helper.Config;
import com.nwu.data.taxi.service.helper.model.PerformancePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class APIController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GPSReadingService gpsReadingService;
    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private KalService kalService;


    @RequestMapping(method = RequestMethod.GET, path = "/gpsreading/load")
    public @ResponseBody
    long loadGPSReading() {
        return gpsReadingService.loadGPSReading();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/passenger/load")
    public @ResponseBody
    long loadPassengers(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "50") int pageSize) {
        return gpsReadingService.loadPassenger(pageNum, pageSize);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/gps_data/load/{index}")
    public @ResponseBody
    long loadGPSData(@PathVariable("index") int index) {
        return gpsReadingService.loadGPSData(index);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/route_data/load")
    public @ResponseBody
    long loadRouteData(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "50") int pageSize) {
        return gpsReadingService.loadRoutData(pageNum, pageSize);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/grid_reading/load")
    public @ResponseBody
    long loadGridReading(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "50") int pageSize) {
        return gpsReadingService.loadGridReading(pageNum, pageSize);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/grid_probability/load")
    public @ResponseBody
    long loadGridProbability(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "50") int pageSize) {
        return gpsReadingService.loadGridProbability(pageNum, pageSize);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/result")
    public @ResponseBody
    List<PerformancePage> getPerformanceData(@RequestParam(value = "date", defaultValue = "0519") String date) {
        return performanceService.getPerformancePage("2008" + date);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/grid_probability")
    public @ResponseBody
    Iterable<GridProbability> getGridProbability(@RequestParam(value = "date", defaultValue = "0531") String date, @RequestParam(value = "timeChunk", defaultValue = "30") int timeChunk) {
        return gpsReadingService.getGridProbability("2008" + date, timeChunk);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/cal_kal")
    public @ResponseBody
    String calKal(@RequestParam(value = "date", defaultValue = "0519") String date) {
//        String[] DATES = {"0526","0529","0530","0601","0602","0605","0606","0607","0608","0609"};
        String[] DATES = {"0601","0602","0605","0606","0607","0608","0609"};
        for (String test : DATES) {
            for (String dayHalfHour : Config.DAY_HALF_HOURS) {
                kalService.calKalProbability("2008" + test, dayHalfHour);
            }
            logger.info("Saved DATE: " + test);
        }

        return "start";
    }

}
