package com.nwu.data.taxi.service.helper.recommender;


import com.nwu.data.taxi.service.helper.Recommender;
import com.nwu.data.taxi.service.helper.model.Grid;
import com.nwu.data.taxi.service.helper.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MyRecommender implements Recommender {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void recommend(List<Vehicle> vehicles, HashMap<Integer, Grid> graph) {
        List<Grid> highProbabilityGrids = getHighProbabilityGrids(graph);
        assignHighProbabilityGridsToVehicles(vehicles, highProbabilityGrids);
        vehicles.forEach(vehicle -> assignRoute(vehicle));
    }

    private void assignRoute(Vehicle v) {
        logger.info("assigning route to vehicle " + v.getName());
        List<Grid> cluster = new ArrayList<>(v.getCluster());
        List<Grid> route = new ArrayList<>();
        Grid currentGrid = v.getCurrentGrid();
        while (!cluster.isEmpty()) {
            route.addAll(bestRoute(currentGrid, cluster));
            currentGrid = route.get(route.size() - 1);
            cluster.remove(currentGrid);
        }
        v.setRoute(route);
    }

    private List<Grid> bestRoute(Grid currentGrid, List<Grid> cluster) {
        double objective = Double.POSITIVE_INFINITY;
        List<Grid> bestRoute = null;
        for (Grid destination : cluster) {
            double localObjective = currentGrid.getDistance(destination);
            if ((null == bestRoute || localObjective < objective)) {
                List<Grid> route = calculateBestRoute(currentGrid, destination);
                if (null != route) {
                    objective = localObjective;
                    bestRoute = route;
                }
            }
        }
        return bestRoute;
    }

    private List<Grid> calculateBestRoute(Grid from, Grid to) {
        HashMap<Grid, List<Grid>> routes = new HashMap<>();
        HashMap<Grid, Double> objective = new HashMap<>();
        HashMap<Grid, Double> probabilitySuccess = new HashMap<>();
        HashMap<Grid, Double> probabilityFailure = new HashMap<>();
        HashMap<Grid, Double> distance = new HashMap<>();
        List<Grid> visited = new ArrayList<>();
        objective.put(to, Double.POSITIVE_INFINITY);
        for (Grid g : from.getNeighbors()) {
            probabilitySuccess.put(g, g.getProbability());
            probabilityFailure.put(g, 1 - g.getProbability());
            distance.put(g, from.getDistance(g));
            objective.put(g, probabilitySuccess.get(g) * distance.get(g));
            routes.put(g, new ArrayList<>());
            routes.get(g).add(g);
        }
        while (!visited.contains(to)) {
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
                    double localObjective = minObjective
                            + probabilityFailure.get(gMin) * g.getProbability()
                            * (distance.get(gMin) + gMin.getDistance(g));
                    ArrayList<Grid> localRoute = new ArrayList<>();
                    localRoute.addAll(routes.get(gMin));
                    localRoute.add(g);
                    if (objective.get(g) == null
                            || objective.get(g) > localObjective) {
                        objective.put(g, localObjective);
                        routes.put(g, localRoute);
                        probabilitySuccess.put(g, probabilityFailure.get(gMin)
                                * g.getProbability());
                        probabilityFailure.put(g, probabilityFailure.get(gMin)
                                * (1 - g.getProbability()));
                        distance.put(g,
                                distance.get(gMin) + gMin.getDistance(g));
                    }
                }
            }
        }
        return routes.get(to);

    }

    private void assignHighProbabilityGridsToVehicles(List<Vehicle> vehicles, List<Grid> highProbabilityGrids) {
        for (Grid g : highProbabilityGrids) {
            vehicles.sort((v1, v2) -> orderByDistance(v1, v2, g));
            for (int i = 0; i < g.getMaxNumberOfTaxis() && i< vehicles.size(); i++) {
                vehicles.get(i).getCluster().add(g);
                g.setMaxNumberOfTaxis(g.getMaxNumberOfTaxis() - 1);
            }

        }

        vehicles.sort(this::orderByCluster);
        /*
         * In case the vehicle's cluster is empty
		 */
        for (int i = 0; i < vehicles.size() / 2; i++) {
            Vehicle current = vehicles.get(i);
            Vehicle last = vehicles.get(vehicles.size() - 1 - i);
            if ((current.getCluster().size() > 0) || (last.getCluster().size() < 2)) {
                break;
            }
            double minDistance = Double.POSITIVE_INFINITY;
            Grid minG = null;
            for (Grid g : last.getCluster()) {
                if (current.getCurrentGrid().getDistance(g) < minDistance) {
                    minDistance = current.getCurrentGrid().getDistance(g);
                    minG = g;
                }
            }
            last.getCluster().remove(minG);
            current.getCluster().add(minG);
        }
    }

    private int orderByCluster(Vehicle v1, Vehicle v2) {
        return v1.getCluster().size() - v2.getCluster().size();
    }

    private int orderByDistance(Vehicle v1, Vehicle v2, Grid g) {
        Grid v1_grid = v1.getCurrentGrid();
        Grid v2_grid = v2.getCurrentGrid();
        boolean result;
        if (null == v1_grid) {
            result = true;
        } else if (null == v2) {
            result = false;
        } else {
            result = v1_grid.getDistance(g) - v2_grid.getDistance(g) >= 0;
        }
        return result ? 1 : -1;
    }

    private List<Grid> getHighProbabilityGrids(HashMap<Integer, Grid> graph) {
        List<Grid> highProbabilityGrids = new ArrayList<>();
        for (Grid g : graph.values()) {
            if (g.isHighProbability())
                highProbabilityGrids.add(g);
        }
        return highProbabilityGrids;
    }

}
