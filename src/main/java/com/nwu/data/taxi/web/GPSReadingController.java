package com.nwu.data.taxi.web;

import com.nwu.data.taxi.domain.model.*;
import com.nwu.data.taxi.service.GPSReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class GPSReadingController {
    @Autowired
    private GPSReadingService gpsReadingService;

    @RequestMapping(method = RequestMethod.GET, path = "/gpsreading/load")
    public @ResponseBody long loadGPSReading () {
        return gpsReadingService.loadGPSReading();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/passenger/load")
    public @ResponseBody long loadPassengers (@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "50") int pageSize) {
        return gpsReadingService.loadPassenger(pageNum, pageSize);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/gps_data/load/{index}")
    public @ResponseBody long loadGPSData (@PathVariable("index") int index) {
        return gpsReadingService.loadGPSData(index);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/route_data/load")
    public @ResponseBody long loadRouteData (@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "50") int pageSize){
        return gpsReadingService.loadRoutData(pageNum, pageSize);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/grid_reading/load")
    public @ResponseBody long loadGridReading (@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "50") int pageSize){
        return gpsReadingService.loadGridReading(pageNum, pageSize);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/grid_probability/load")
    public @ResponseBody long loadGridProbability (@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "50") int pageSize){
        return gpsReadingService.loadGridProbability(pageNum, pageSize);
    }
}
