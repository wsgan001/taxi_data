package com.nwu.data.taxi.service.helper.processor;

import com.nwu.data.taxi.domain.model.GPSData;
import com.nwu.data.taxi.domain.model.Route;
import com.nwu.data.taxi.domain.model.Taxi;
import com.nwu.data.taxi.domain.repository.RouteRepository;
import com.nwu.data.taxi.domain.repository.TaxiRepository;
import com.nwu.data.taxi.service.helper.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class RouteProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private TaxiRepository taxiRepository;
    private RouteRepository routeRepository;
    private List<Route> routes;
    private long[][] time;
    private int[][] count;

    public RouteProcessor(RouteRepository routeRepository, TaxiRepository taxiRepository) {
        this.taxiRepository = taxiRepository;
        this.routeRepository = routeRepository;
        int number = Config.NUM_OF_LAT_BINS * Config.NUM_OF_LON_BINS;
        time = new long[number][number];
        count = new int[number][number];
    }


    public void process(PageRequest pageRequest) {
        routes = new ArrayList<>();
        this.routeRepository.findAll().forEach(route -> processRouteData(route));
        this.routeRepository.deleteAll();
        this.taxiRepository.findAll(pageRequest).forEach(taxi -> processTaxi(taxi));
        saveRouteData();
    }

    private void processRouteData(Route route) {
        time[route.getFromGrid()][route.getToGrid()] = route.getDuration();
        count[route.getFromGrid()][route.getToGrid()] = route.getCount();
    }

    private void processTaxi(Taxi taxi) {
        GPSData last = null;
        for(GPSData current : taxi.getGpsData()) {
            if(null != last && Math.abs(last.getTime()-current.getTime()) < Config.MAX_TIME_INTERVAL) {
                if(isAdjacent(current, last)){
                    time[current.getGrid()][last.getGrid()] += (last.getTime() - current.getTime());
                    count[current.getGrid()][last.getGrid()] ++;
                }
            }
            last = current;
        }
        logger.info("Taxi: " + taxi.getName() + "is done. id: " + taxi.getId());
    }
    
    private void saveRouteData(){
        for (int i = 0; i < time.length; i++) {
            for (int j = 0; j < time[i].length ; j++) {
                if (time[i][j] > 0) {
                    routes.add(new Route(i, j, time[i][j], count[i][j]));
                }
            }
        }
        routeRepository.save(routes);
    }


    private boolean isAdjacent(GPSData current, GPSData last) {
        if ( last.getLatBin() >= Config.NUM_OF_LAT_BINS || last.getLatBin() < 0)
            return false;
        if ( current.getLatBin() >= Config.NUM_OF_LAT_BINS || current.getLatBin() < 0)
            return false;
        if ( last.getLonBin() >= Config.NUM_OF_LON_BINS || last.getLonBin() < 0)
            return false;
        if ( current.getLonBin() >= Config.NUM_OF_LON_BINS || current.getLonBin() < 0)
            return false;
        if (Math.abs(last.getLatBin() - current.getLatBin()) <= 1 && Math.abs(last.getLonBin() - current.getLonBin()) <=1)
                return true;
        return false;
    }


}
