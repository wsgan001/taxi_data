package com.nwu.data.taxi.web;

import com.nwu.data.taxi.domain.model.*;
import com.nwu.data.taxi.service.GPSReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/taxi")
public class GPSReadingController {

    @Autowired
    private GPSReadingService gpsReadingService;

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public @ResponseBody Taxi getTaxi () {
        return gpsReadingService.getTaxi();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/gps_reading")
    public @ResponseBody Iterable<GPSReading> getGPSReading (@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "50") int pageSize) {
        return gpsReadingService.getGPSReading(pageNum, pageSize);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/passengers")
    public @ResponseBody Iterable<Passenger> getPassengers (@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "50") int pageSize) {
        return gpsReadingService.getPassenger(pageNum, pageSize);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/gps_data")
    public @ResponseBody Iterable<GPSData> getGPSData (@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "50") int pageSize) {
        return gpsReadingService.getGPSData(pageNum, pageSize);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/trip_event")
    public @ResponseBody Iterable<TripEvent> getTripEvent (@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "50") int pageSize) {
        return gpsReadingService.getTripEvent(pageNum, pageSize);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/route")
    public @ResponseBody Iterable<Route> getRoute (@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "50") int pageSize) {
        return gpsReadingService.getRoute(pageNum, pageSize);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/gps_reading/load")
    public @ResponseBody long loadGPSReading () {
        return gpsReadingService.loadGPSReading();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/passengers/load/{pageNum}")
    public @ResponseBody long loadPassengers (@PathVariable("pageNum") Integer pageNum , @RequestParam("pageSize") Integer pageSize) {
        return gpsReadingService.loadPassenger(pageNum, pageSize);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/gps_data/load/{index}")
    public @ResponseBody long loadGPSData (@PathVariable("index") Integer index) {
        return gpsReadingService.loadGPSData(index);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/route_data/load")
    public @ResponseBody long loadRouteData (@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "50") int pageSize){
        return gpsReadingService.loadRoutData(pageNum, pageSize);
    }
}
