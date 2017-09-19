package com.nwu.data.taxi.service.helper.task;

import com.nwu.data.taxi.service.helper.Task;
import com.nwu.data.taxi.service.helper.model.Grid;
import com.nwu.data.taxi.service.helper.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class VehicleTurnOffTask implements Task {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Vehicle vehicle;
    private long period;
    private Grid location;

    public VehicleTurnOffTask(Vehicle vehicle, long time, Grid location) {
        this.vehicle = vehicle;
        this.period = time;
        this.location = location;
    }

    @Override
    public void execute(HashMap<Long, List<Task>> tasks, long currentTime,
                        HashMap<Integer, Grid> graph) {
        vehicle.setOff();
        vehicle.getRoute().clear();
        if (null != vehicle.getCluster() && vehicle.getCluster().size() > 0) {
            for (Grid g : vehicle.getCluster())
                g.setMaxNumberOfTaxis(g.getMaxNumberOfTaxis() + 1);
            vehicle.getCluster().clear();
        }
        logger.info(vehicle.getName() + " turned off");
        long time = currentTime + period;
        tasks.computeIfAbsent(time, k -> new ArrayList<>());
        tasks.get(time).add(new VehicleTurnOnTask(vehicle, location));
        logger.info("Will turn on again at : " + time);
    }
}
