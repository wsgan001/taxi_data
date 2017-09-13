package com.nwu.data.taxi.service.helper;

import com.nwu.data.taxi.domain.model.GPSData;
import com.nwu.data.taxi.domain.model.Passenger;
import com.nwu.data.taxi.domain.model.Taxi;
import com.nwu.data.taxi.domain.model.TripEvent;
import com.nwu.data.taxi.domain.repository.PassengerRepository;
import com.nwu.data.taxi.domain.repository.TaxiRepository;
import com.nwu.data.taxi.domain.repository.TripEventRepository;
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
    private List<Passenger> passengers;
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
        passengers = new ArrayList<>();
        TripEvent lastEvent = tripEventRepository.findTopByOrderByIdDesc();
        if ((null == lastEvent) || (count >= lastEvent.getTaxi().getId())) {
            taxiRepository.findAll(page).forEach(taxi -> processTaxi(taxi));
        }
        logger.info("Start saving passenger data");
        passengerRepository.save(passengers);
        logger.info("Start saving trip event data");
        tripEventRepository.save(tripEvents);
        logger.info("End saving data");
    }

    private void processTaxi(Taxi taxi) {

        Iterable<GPSData> gpsData = taxi.getGpsData();
        GPSData dropOffLocation = null;
        GPSData last = null;
        for (GPSData current : gpsData) {
            if (null == last) {
                if (current.isOccupied()) {
                    dropOffLocation = current;
                }
            } else {
                if (!last.getDateString().equals(current.getDateString()))
                    addTurnOnEvent(last);
                if (last.getTime() - current.getTime() > Config.MAX_TIME_INTERVAL) {
                    addTurnOffEvent(current, last);
                    if (null != dropOffLocation) {
                        addPassenger(last, dropOffLocation);
                        dropOffLocation = null;
                    }

                } else {
                    if (current.isOccupied() && !last.isOccupied()) {
                        // drop off
                        dropOffLocation = last;
                    } else if (!current.isOccupied()
                            && last.isOccupied()) {
                        // pickup
                        addPassenger(last, dropOffLocation);
                        dropOffLocation = null;
                    }
                }
            }
            last = current;
        }
        count++;

        logger.info("Taxi : " + taxi.getName() + ", id: " + taxi.getId() + " is done.(" + count + "/" + total + ")");
    }

    private void addPassenger(GPSData start, GPSData end) {
        passengers.add( new Passenger(start, end));
    }

    private void addTurnOnEvent(GPSData last) {
        tripEvents.add(new TripEvent(Long.parseLong(last.getDateString()), Long.parseLong(last.getTimeString()), TripEvent.TURN_ON, last.getGrid(),  last.getTime(), 0, last.getTaxi()));
    }

    private void addTurnOffEvent(GPSData current, GPSData last){
        long turnOffDate = Long.parseLong(current.getDateString());
        long turnOffTime = Long.parseLong(current.getTimeString());
        long turnOffDateTime = current.getTime();
        long duration = last.getTime() - current.getTime();
        tripEvents.add(new TripEvent(turnOffDate, turnOffTime, TripEvent.TURN_OFF, last.getGrid(), turnOffDateTime, duration, last.getTaxi()));
    }
}
