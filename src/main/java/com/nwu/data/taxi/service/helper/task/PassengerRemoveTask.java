package com.nwu.data.taxi.service.helper.task;

import com.nwu.data.taxi.service.helper.Task;
import com.nwu.data.taxi.service.helper.model.Grid;
import com.nwu.data.taxi.service.helper.model.Passenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class PassengerRemoveTask implements Task {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    Passenger passenger;

    public PassengerRemoveTask(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public void execute(HashMap<Long, List<Task>> tasks, long currentTime,
                        HashMap<Integer, Grid> graph) {
        passenger.getLocation().getPassengers().remove(passenger);
        logger.info("Passenger removed from " + passenger.getLocation());
    }
}
