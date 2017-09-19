package com.nwu.data.taxi.service.helper.task;

import com.nwu.data.taxi.service.helper.Task;
import com.nwu.data.taxi.service.helper.model.Grid;
import com.nwu.data.taxi.service.helper.model.Passenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PassengerAddTask implements Task {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    Passenger passenger;

    public PassengerAddTask(Passenger p) {
        this.passenger = p;
    }

    @Override
    public void execute(HashMap<Long, List<Task>> tasks, long currentTime,
                        HashMap<Integer, Grid> graph) {
        passenger.getLocation().getPassengers().add(passenger);
        logger.info("Passenger Created in " + passenger.getLocation());
        logger.info("Passenger will be removed at " + currentTime
                + passenger.getWaitingTime());
        tasks.computeIfAbsent(currentTime + passenger.getWaitingTime(), k -> new ArrayList<>());
        tasks.get(currentTime + passenger.getWaitingTime()).add(
                new PassengerRemoveTask(passenger));
    }
}
