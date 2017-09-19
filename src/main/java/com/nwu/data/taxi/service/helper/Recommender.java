package com.nwu.data.taxi.service.helper;

import com.nwu.data.taxi.service.helper.model.Grid;
import com.nwu.data.taxi.service.helper.model.Vehicle;

import java.util.HashMap;
import java.util.List;

public interface Recommender {

    void recommend(List<Vehicle> vehiclesNeedRecommendation,
                   HashMap<Integer, Grid> graph);
}
