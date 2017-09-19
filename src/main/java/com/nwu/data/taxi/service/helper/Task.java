package com.nwu.data.taxi.service.helper;

import com.nwu.data.taxi.service.helper.model.Grid;

import java.util.HashMap;
import java.util.List;

public interface Task {
    void execute(HashMap<Long, List<Task>> tasks, long currentTime, HashMap<Integer, Grid> graph);
}
