package com.nwu.data.taxi.service;


import com.nwu.data.taxi.domain.model.*;
import com.nwu.data.taxi.domain.repository.*;
import com.nwu.data.taxi.service.helper.processor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GPSReadingService {

    @Autowired
    private GPSDataRepository gpsDataRepository;
    @Autowired
    private GPSReadingRepository gpsReadingRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private TripEventRepository tripEventRepository;
    @Autowired
    private TaxiRepository taxiRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private GridReadingRepository gridReadingRepository;
    @Autowired
    private GridProbabilityRepository gridProbabilityRepository;

    private Pageable pageRequest = new PageRequest(0, 100);

    public long loadGPSData(int index) {
        GPSDataProcessor inputProcessor = new GPSDataProcessor("txt");
        inputProcessor.processFiles(gpsDataRepository, index);
        return gpsDataRepository.count();
    }

    public long loadGPSReading() {
        GPSDataProcessor inputProcessor = new GPSDataProcessor("txt");
        inputProcessor.processFiles((byte)0, (byte)1, gpsReadingRepository);
        return gpsReadingRepository.count();
    }

    public long loadPassenger(int pageNum, int pageSize) {
        TurnOnTurnOffProcessor turnOnTurnOffProcessor = new TurnOnTurnOffProcessor(passengerRepository, tripEventRepository, taxiRepository);
        turnOnTurnOffProcessor.extractTurnOnTurnOff(new PageRequest(pageNum, pageSize));
        return pageNum;
    }


    public Iterable<GPSReading> getGPSReading(int pageNum, int pageSize) {
        return gpsReadingRepository.findAll(new PageRequest(pageNum, pageSize));
    }


    public Iterable<PassengerData> getPassenger(int pageNum, int pageSize) {
        return passengerRepository.findAll(new PageRequest(pageNum, pageSize));
    }

    public Iterable<GPSData> getGPSData(int pageNum, int pageSize) {
        return gpsDataRepository.findAll(new PageRequest(pageNum, pageSize));
    }

    public Iterable<Taxi> getTaxis() {
        return taxiRepository.findAll();
    }

    public Iterable<TripEvent> getTripEvent(int pageNum, int pageSize) {
        return tripEventRepository.findAll(new PageRequest(pageNum, pageSize));
    }

    public long loadRoutData(int pageNum, int pageSize) {
        RouteProcessor routeProcessor = new RouteProcessor(routeRepository, taxiRepository);
        routeProcessor.process(new PageRequest(pageNum, pageSize));
        return routeRepository.count();
    }

    public Iterable<Route> getRoute(int pageNum, int pageSize) {
         return routeRepository.findAll(new PageRequest(pageNum, pageSize));
    }

    public long loadGridReading(int pageNum, int pageSize) {
        GridReadingProcessor gridReadingProcessor = new GridReadingProcessor(gridReadingRepository, taxiRepository);
        gridReadingProcessor.process(new PageRequest(pageNum, pageSize));
        return pageNum;
    }

    public Iterable<GridReading> getGridReading(int pageNum, int pageSize) {
        return gridReadingRepository.findAll(new PageRequest(pageNum, pageSize));
    }

    public long loadGridProbability(int pageNum, int pageSize) {
        ProbabilityProcessor probabilityProcessor = new ProbabilityProcessor(gridProbabilityRepository, taxiRepository);
        probabilityProcessor.process(new PageRequest(pageNum, pageSize));
        return pageNum;
    }

    public Iterable<GridProbability> getGridProbability(int pageNum, int pageSize) {
        return gridProbabilityRepository.findAll(new PageRequest(pageNum, pageSize));
    }
}
