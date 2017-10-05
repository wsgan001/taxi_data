package com.nwu.data.taxi.service.helper.task;

import com.nwu.data.taxi.service.helper.Task;
import com.nwu.data.taxi.service.helper.model.Grid;
import com.nwu.data.taxi.service.helper.model.Passenger;
import com.nwu.data.taxi.service.helper.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.ManyToOne;
import java.util.*;


public class VehicleMovementTask implements Task {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Vehicle vehicle;

    public VehicleMovementTask(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public void execute(HashMap<Long, List<Task>> tasks, long currentTime,
                        HashMap<Integer, Grid> graph) {
        if (vehicle.isOn()) {
            Grid from = vehicle.getCurrentGrid();
            logger.info(vehicle.getName() + " reached " + from);
            assignPassenger();
            if (null != vehicle.getRoute() && vehicle.getRoute().size() > 0) {
                Grid to = vehicle.getRoute().get(0);
                recordDistanceAndTime(from, to);
                addNextMovementTask(tasks, currentTime, from, to);
            }
        }
    }

    private void assignPassenger() {
        Grid currentGrid = vehicle.getCurrentGrid();
        List<Passenger> currentGridPassengers = currentGrid.getPassengers();
        if (!vehicle.isOccupied() && null != currentGridPassengers && !currentGridPassengers.isEmpty()) {
            List<Grid> path;
            Passenger passenger = currentGridPassengers.get(0);
            path = getShortestPath(currentGrid, passenger.getDestination());
            if (null != path){
                vehicle.hunt();
                logger.info(vehicle.getName() + " HUNT!");
                freeCluster(vehicle);
                vehicle.setCluster(new ArrayList<>());
                vehicle.getCluster().add(passenger.getDestination());
                vehicle.setRoute(path);
                //assuming that all the other passengers are taking by the other no-need-recommend vehicles
                currentGridPassengers.clear();
            }
        }
    }

    private void recordDistanceAndTime(Grid from, Grid to) {
        vehicle.getRoute().remove(0);
        vehicle.setCurrentGrid(to);
        double distance = from.getDistance(to);
        double tripTime = from.getTime(to);
        vehicle.setTravelDistance(vehicle.getTravelDistance() + distance);
        vehicle.setTravelTime(vehicle.getTravelTime() + tripTime);
        if (vehicle.isOccupied()) {
            vehicle.setLiveDistance(vehicle.getLiveDistance() + distance);
            vehicle.setLiveTime(vehicle.getLiveTime() + tripTime);
        }
        if (vehicle.isOccupied() && vehicle.getRoute().isEmpty())
            vehicle.setOccupied(false);
        if (vehicle.getCluster().contains(from)) {
            vehicle.getCluster().remove(from);
            from.setMaxNumberOfTaxis(from.getMaxNumberOfTaxis() + 1);
        }
    }

    private void addNextMovementTask(HashMap<Long, List<Task>> tasks, long currentTime, Grid currentGrid, Grid to) {
        long time = currentTime + (long) currentGrid.getTime(to) + 1;
        List<Task> taskList = tasks.computeIfAbsent(time, k -> new ArrayList<>());
        taskList.add(new VehicleMovementTask(vehicle));
        logger.info("Task created for time " + time);
    }


    private void freeCluster(Vehicle vehicle) {
        for (Grid grid : vehicle.getCluster())
            grid.setMaxNumberOfTaxis(grid.getMaxNumberOfTaxis() + 1);
    }

    private List<Grid> getShortestPath(Grid start, Grid end) {
        HashMap<Grid, List<Grid>> routes = new HashMap<>();
        HashMap<Grid, Double> objective = new HashMap<>();
        List<Grid> visited = new ArrayList<>();
        objective.put(end, Double.POSITIVE_INFINITY);
        for (Grid g : start.getNeighbors()) {
            objective.put(g, start.getDistance(g));
            routes.put(g, new ArrayList<>());
            routes.get(g).add(g);
        }
        while (!visited.contains(end)) {
            Grid gMin = null;
            double minObjective = Double.POSITIVE_INFINITY;
            for (Grid g : objective.keySet()) {
                if (minObjective > objective.get(g) && !visited.contains(g)) {
                    gMin = g;
                    minObjective = objective.get(g);
                }
            }
            if (gMin == null) {
                return null;
            }
            visited.add(gMin);
            if (gMin.getNeighbors() != null) {
                for (Grid g : gMin.getNeighbors()) {
                    double localObjective = minObjective + gMin.getDistance(g);
                    List<Grid> localRoute = new ArrayList<>();
                    localRoute.addAll(routes.get(gMin));
                    localRoute.add(g);
                    if (objective.get(g) == null
                            || objective.get(g) > localObjective) {
                        objective.put(g, localObjective);
                        routes.put(g, localRoute);
                    }
                }
            }
        }
        return routes.get(end);
    }


}