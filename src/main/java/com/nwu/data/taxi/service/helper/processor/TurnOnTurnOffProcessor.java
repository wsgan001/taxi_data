package com.nwu.data.taxi.service.helper.processor;

import com.nwu.data.taxi.domain.model.GPSData;
import com.nwu.data.taxi.domain.model.PassengerData;
import com.nwu.data.taxi.domain.model.Taxi;
import com.nwu.data.taxi.domain.model.TripEvent;
import com.nwu.data.taxi.domain.repository.PassengerRepository;
import com.nwu.data.taxi.domain.repository.TaxiRepository;
import com.nwu.data.taxi.domain.repository.TripEventRepository;
import com.nwu.data.taxi.service.helper.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class TurnOnTurnOffProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private PassengerRepository passengerRepository;
    private TripEventRepository tripEventRepository;
    private TaxiRepository taxiRepository;
    private List<TripEvent> tripEvents;
    private List<PassengerData> passengerData;
    private int count;
    private long total;

    public TurnOnTurnOffProcessor(PassengerRepository passengerRepository, TripEventRepository tripEventRepository, TaxiRepository taxiRepository) {
        this.passengerRepository = passengerRepository;
        this.tripEventRepository = tripEventRepository;
        this.taxiRepository = taxiRepository;
    }

    public void extractTurnOnTurnOff(Pageable page) {
        logger.info("Start to process GPS Data");
        count += page.getPageNumber() * page.getPageSize();
        total = taxiRepository.count();
        tripEvents = new ArrayList<>();
        passengerData = new ArrayList<>();
        TripEvent lastEvent = tripEventRepository.findTopByOrderByIdDesc();
        if ((null == lastEvent) || (count >= lastEvent.getTaxi().getId())) {
            taxiRepository.findAll(page).forEach(taxi -> processTaxi(taxi));
        }
        logger.info("Start saving passenger data");
        passengerRepository.save(passengerData);
        logger.info("Start saving trip event data");
        tripEventRepository.save(tripEvents);
        logger.info("End saving data");
    }

    private void processTaxi(Taxi taxi) {
        Iterable<GPSData> gpsData = taxi.getGpsData();
        GPSData pickup = null;
        GPSData previous = null;
        for (GPSData current : gpsData) {
            if (null == previous || !previous.getDateString().equals(current.getDateString())) {
                if (null != pickup) {
                    addPassenger(pickup, previous);
                    pickup = null;
                }
                if (current.isOccupied()) {
                    pickup = current;
                }
                addTurnOnEvent(current);
            } else {
                if (current.getTime() - previous.getTime() > Config.MAX_TIME_INTERVAL) {
                    addTurnOffEvent(previous, current);
                    if (null != pickup) {
                        addPassenger(pickup, previous);
                        pickup = null;
                    }
                    if (current.isOccupied()) {
                        pickup = current;
                    }
                } else {
                    if (previous.isOccupied() && !current.isOccupied()) {
                        addPassenger(pickup, current);
                        pickup = null;
                    } else if (!previous.isOccupied() && current.isOccupied()) {
                        pickup = current;
                    }
                }
            }
            previous = current;
        }
        count++;

        logger.info("Taxi : " + taxi.getName() + ", id: " + taxi.getId() + " is done.(" + count + "/" + total + ")");
    }

    private void addPassenger(GPSData start, GPSData end) {
        passengerData.add( new PassengerData(start, end));
    }

    private void addTurnOnEvent(GPSData last) {
        tripEvents.add(new TripEvent(last.getDateString(), last.getTimeString(), TripEvent.TURN_ON, last.getGrid(),  last.getTime(), 0, last.getTaxi()));
    }

    private void addTurnOffEvent(GPSData previous, GPSData current){
        String turnOffDate = previous.getDateString();
        String turnOffTime = previous.getTimeString();
        long turnOffDateTime = previous.getTime();
        long duration = current.getTime() - previous.getTime();
        tripEvents.add(new TripEvent(turnOffDate, turnOffTime, TripEvent.TURN_OFF, current.getGrid(), turnOffDateTime, duration, current.getTaxi()));
    }
}
