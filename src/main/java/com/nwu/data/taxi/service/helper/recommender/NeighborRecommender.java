package com.nwu.data.taxi.service.helper.recommender;

import com.nwu.data.taxi.service.helper.Recommender;
import com.nwu.data.taxi.service.helper.model.Grid;
import com.nwu.data.taxi.service.helper.model.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NeighborRecommender implements Recommender {

    @Override
    public void recommend(List<Vehicle> vehicles, HashMap<Integer, Grid> graph) {
        vehicles.forEach(vehicle -> recommendRoute(vehicle));
    }

    private void recommendRoute(Vehicle vehicle) {
        Grid selected = null;
        for (Grid g : vehicle.getCurrentGrid().getNeighbors()) {
            if (selected == null || g.getProbability() > selected.getProbability())
                selected = g;
        }
        vehicle.setCluster(new ArrayList<>());
        vehicle.getCluster().add(selected);
        vehicle.setRoute(new ArrayList<>());
        vehicle.getRoute().add(selected);
    }
}
