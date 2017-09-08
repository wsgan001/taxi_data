package com.nwu.data.taxi.service;


import com.nwu.data.taxi.domain.model.GPSData;
import com.nwu.data.taxi.domain.model.GPSReading;
import com.nwu.data.taxi.domain.model.Passenger;
import com.nwu.data.taxi.domain.repository.GPSDataRepository;
import com.nwu.data.taxi.domain.repository.GPSReadingRepository;
import com.nwu.data.taxi.domain.repository.PassengerRepository;
import com.nwu.data.taxi.domain.repository.TripEventRepository;
import com.nwu.data.taxi.service.helper.RawInputProcessor;
import com.nwu.data.taxi.service.helper.TurnOnTurnOffProcessor;
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
    private Pageable pageRequest = new PageRequest(0, 100);

    public long loadGPSData(Integer index) {
        RawInputProcessor inputProcessor = new RawInputProcessor("txt");
        inputProcessor.processFiles(gpsDataRepository, index);
        return gpsDataRepository.count();
    }

    public long loadGPSReading() {
        RawInputProcessor inputProcessor = new RawInputProcessor("txt");
        inputProcessor.processFiles((byte)0, (byte)1, gpsReadingRepository);
        return gpsReadingRepository.count();
    }

    public long loadPassenger() {
        TurnOnTurnOffProcessor turnOnTurnOffProcessor = new TurnOnTurnOffProcessor(passengerRepository, tripEventRepository);
        turnOnTurnOffProcessor.extractPassengers();
        return passengerRepository.count();
    }


    public Iterable<GPSReading> getGPSReading() {
        return gpsReadingRepository.findAll(pageRequest);
    }


    public Iterable<Passenger> getPassenger() {
        return passengerRepository.findAll(pageRequest);
    }

    public Iterable<GPSData> getGPSData() {
        return gpsDataRepository.findAll(pageRequest);
    }

}
