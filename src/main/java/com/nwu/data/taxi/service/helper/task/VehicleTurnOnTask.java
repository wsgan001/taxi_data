package com.nwu.data.taxi.service.helper.task;

import com.nwu.data.taxi.service.helper.model.Grid;
import com.nwu.data.taxi.service.helper.Task;
import com.nwu.data.taxi.service.helper.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class VehicleTurnOnTask implements Task {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Vehicle vehicle;
    private Grid currentGrid;

    public VehicleTurnOnTask(Vehicle vehicle, Grid currentGrid) {
        this.vehicle = vehicle;
        this.currentGrid = currentGrid;
    }

    @Override
    public void execute(HashMap<Long, List<Task>> tasks, long currentTime, HashMap<Integer, Grid> graph) {
        vehicle.setCurrentGrid(currentGrid);
        vehicle.setOn();
        vehicle.getRoute().clear();
        if (null != vehicle.getCluster() && vehicle.getCluster().size() > 0) {
            for (Grid g : vehicle.getCluster())
                g.setMaxNumberOfTaxis(g.getMaxNumberOfTaxis() + 1);
            vehicle.getCluster().clear();
        }
        logger.info(vehicle.getName() + " Turned On in " + vehicle.getCurrentGrid());
        tasks.computeIfAbsent(currentTime + 1, k -> new ArrayList<>());
        tasks.get(currentTime + 1).add(new VehicleMovementTask(vehicle));
    }
}
