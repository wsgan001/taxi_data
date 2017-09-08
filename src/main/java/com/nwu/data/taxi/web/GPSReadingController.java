package com.nwu.data.taxi.web;

import com.nwu.data.taxi.domain.model.GPSData;
import com.nwu.data.taxi.domain.model.GPSReading;
import com.nwu.data.taxi.domain.model.Passenger;
import com.nwu.data.taxi.service.GPSReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/taxi")
public class GPSReadingController {

    @Autowired
    private GPSReadingService gpsReadingService;

    @RequestMapping(method = RequestMethod.GET, path = "/gps_reading")
    public @ResponseBody Iterable<GPSReading> getGPSReading () {
        return gpsReadingService.getGPSReading();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/passengers")
    public @ResponseBody Iterable<Passenger> getPassengers () {
        return gpsReadingService.getPassenger();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/gps_data")
    public @ResponseBody Iterable<GPSData> getGPSData () {
        return gpsReadingService.getGPSData();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/gps_reading/load")
    public @ResponseBody long loadGPSReading () {
        return gpsReadingService.loadGPSReading();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/passengers/load")
    public @ResponseBody long loadPassengers () {
        return gpsReadingService.loadPassenger();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/gps_data/load/{index}")
    public @ResponseBody long loadGPSData (@PathVariable("index") Integer index) {
        return gpsReadingService.loadGPSData(index);
    }
}
