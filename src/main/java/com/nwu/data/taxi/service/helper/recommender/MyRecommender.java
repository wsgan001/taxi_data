package com.nwu.data.taxi.service.helper.recommender;


import com.nwu.data.taxi.service.helper.Recommender;
import com.nwu.data.taxi.service.helper.model.Grid;
import com.nwu.data.taxi.service.helper.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class MyRecommender implements Recommender {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void recommend(List<Vehicle> vehicles, HashMap<Integer, Grid> graph) {
        List<Grid> highProbabilityGrids = getHighProbabilityGrids(graph);
        assignHighProbabilityGridsToVehicles(vehicles, highProbabilityGrids);
        vehicles.forEach(this::assignRoute);
    }

    private void assignRoute(Vehicle v) {
        logger.info("assigning route to vehicle " + v.getName());
        Grid currentGrid = v.getCurrentGrid();
        Grid finalCurrentGrid = currentGrid;
        v.getCluster().sort(Comparator.comparingDouble(c -> c.getDistance(finalCurrentGrid)));
        for (Grid grid : v.getCluster()) {
            List<Grid> route = calculateBestRoute(currentGrid, grid);
            if (null != route && !route.isEmpty()) {
                v.getRoute().addAll(route);
                currentGrid = grid;
            }
        }
    }

    private List<Grid> calculateBestRoute(Grid from, Grid to) {
        if(from.getNeighbors().contains(to)){
            List<Grid> route= new ArrayList<>();
            route.add(to);
            return route;

        } else {
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
                    return new ArrayList<>();
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


    }

    private void assignHighProbabilityGridsToVehicles(List<Vehicle> vehicles, List<Grid> highProbabilityGrids) {
        for (Grid g : highProbabilityGrids) {
            vehicles.sort(Comparator.comparingDouble(v -> v.getCurrentGrid().getDistance(g)));
            for (int i = 0; g.getMaxNumberOfTaxis() > 0 && i< vehicles.size() && !vehicles.get(i).hasEnoughCluster(); i++) {
                vehicles.get(i).getCluster().add(g);
                g.setMaxNumberOfTaxis(g.getMaxNumberOfTaxis() - 1);
            }
        }

        /*
         * In case the vehicle's cluster is empty
		 */

       do {
            vehicles.sort(Comparator.comparingInt(v -> v.getCluster().size()));
            Vehicle current = vehicles.get(0);
            Vehicle last = vehicles.get(vehicles.size() - 1);
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
        } while (vehicles.get(0).getCluster().isEmpty());
    }

    private List<Grid> getHighProbabilityGrids(HashMap<Integer, Grid> graph) {
        return graph.values().stream().filter(Grid::isHighProbability).collect(Collectors.toList());
    }

}
